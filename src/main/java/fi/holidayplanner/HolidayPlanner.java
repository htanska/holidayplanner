package fi.holidayplanner;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import java.lang.Exception;
import java.util.stream.Collectors;

/*
 *
 *
 */
public class HolidayPlanner {

    Calendar startDate, endDate;
    List<Calendar> nationalHolidays;
    Locale locale;

    final SimpleDateFormat SDF = new SimpleDateFormat("d.M.yyyy");
    final int MAX_TIME_SPAN = 50;
    final static String DATE_SPLITTER = "\\.";

    // The implementation needs to take into account that national holidays change from year to year and in the future the usage of the class will be extended to other countries,
    // so it must support national holidays for several countries
    // Holidays will be set as part of a HolidayPlanner objects, and can be set by using files named after language code
    // This is just one very simple (and rough) way to do it
    private List<Calendar> loadLocaleHolidays(String language) throws IOException {
        List<Calendar> holidays = new ArrayList<Calendar>();
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/national.holidays."+language.toLowerCase())));
        if (!br.ready()) {
            throw new IllegalArgumentException("National holidays file was not found!");
        } else {
            String holidaysStr = br.lines().collect(Collectors.joining(";"));
            br.close();
            List<String> holidaysStrList = Arrays.asList(holidaysStr.split(";"));
            for (String holiday : holidaysStrList) {
                String []str = holiday.split(DATE_SPLITTER);
                holidays.add(new GregorianCalendar(Integer.parseInt(str[2]), Integer.parseInt(str[1]) - 1, Integer.parseInt(str[0])));
            }
            return holidays;
        }
    }

    // Constructor
    public HolidayPlanner(Calendar startDate, Calendar endDate, String language) throws Exception {

        // Class won't accept time span if:
        // - dates are not in chronological order
        // - time span doesn't fit within the current holiday period
        // - length of the time span is more 50 days
        if (!isChronologicalOrder(startDate, endDate)) {
            throw new Exception("Holiday period not in chronological order");
        }
        if (!isOnHolidayPeriod(startDate, endDate)) {
            throw new Exception("Invalid holiday period");
        }
        if (!isUnderMaxLength(startDate, endDate)) {
            throw new Exception("Too long holiday period");
        }

        this.nationalHolidays = loadLocaleHolidays(language);
        this.startDate = startDate;
        this.endDate = endDate;
        this.locale = new Locale(language);
    }

    //
    public int getHolidayPeriodConsumableDays() {
        int holidayCount = 0;
        Calendar cal = new GregorianCalendar(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
        do {
            if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && !isNationalHolidayDay(cal)) {
                holidayCount++;
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        } while (!cal.equals(endDate));
        // last day is missing, so check and add last one if is consumable day
        if (endDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && !isNationalHolidayDay(cal)) {
            holidayCount++;
        }
        return holidayCount;
    }

    //
    public int getPeriodLength() {
        int daysCount = 0;
        Calendar cal = new GregorianCalendar(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
        do {
            daysCount++;
            cal.add(Calendar.DAY_OF_YEAR, 1);
        } while (!cal.equals(endDate));
        // last day is missing so add one
        return daysCount + 1;
    }

    //
    private boolean isChronologicalOrder(Calendar startDate, Calendar endDate) {
        return endDate.after(startDate);
    }

    //
    public boolean isChronologicalOrder() {
        return endDate.after(startDate);
    }

    //
    private boolean isOnHolidayPeriod(Calendar startDate, Calendar endDate) {
        int sYear = startDate.get(Calendar.YEAR);
        int eYear = endDate.get(Calendar.YEAR);
        int sMonth = startDate.get(Calendar.MONTH);
        int eMonth = endDate.get(Calendar.MONTH);
        if (startDate.get(Calendar.YEAR) > endDate.get(Calendar.YEAR)) {
            return false;
        }
        else if (sYear == eYear) {
            if (sMonth < 4) {
                return eMonth < 4;
            }
            else {
                return eMonth >= sMonth;
            }
        }
        else {
            return true;
        }
    }

    //
    public boolean isOnHolidayPeriod() {
        int sYear = startDate.get(Calendar.YEAR);
        int eYear = endDate.get(Calendar.YEAR);
        int sMonth = startDate.get(Calendar.MONTH);
        int eMonth = endDate.get(Calendar.MONTH);
        if (startDate.get(Calendar.YEAR) > endDate.get(Calendar.YEAR)) {
            return false;
        }
        else if (sYear == eYear) {
            if (sMonth < 4) {
                return eMonth < 4;
            }
            else {
                return eMonth >= sMonth;
            }
        }
        else {
            return true;
        }
    }

    //
    private boolean isUnderMaxLength(Calendar startDate, Calendar endDate) {
        Calendar test = new GregorianCalendar(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
        test.add(Calendar.DAY_OF_YEAR, MAX_TIME_SPAN);
        return !test.before(endDate);
    }

    //
    public boolean isUnderMaxLength() {
        Calendar test = new GregorianCalendar(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
        test.add(Calendar.DAY_OF_YEAR, MAX_TIME_SPAN);
        return !test.before(endDate);
    }

    private boolean isNationalHolidayDay(Calendar date) {
        for(Calendar holiday : nationalHolidays) {
            if (holiday.equals(date)) {
                return true;
            }
        }
        return false;
    }

    //
    public String getPeriodStartAsString() {
        return (SDF.format(startDate.getTime()));
    }

    //
    public String getPeriodEndAsString() {
        return (SDF.format(endDate.getTime()));
    }

    //
    public String getNationalHolidaysAsString() {
        String holidaysStr = "";
        for (Calendar c : nationalHolidays) {
            holidaysStr += SDF.format(c.getTime()) + " ";
        }
        return holidaysStr;
    }

    //
    public Calendar getStartDate() {
        return this.startDate;
    }

    //
    public Calendar getEndDate() {
        return this.endDate;
    }

    //
    public Locale getLocale() {
        return this.locale;
    }

    //
    public List<Calendar> getNationalHolidays() {
        return this.nationalHolidays;
    }


    //
    public static void main(String []args) throws Exception {


        // Input dates could be set as strings, in which case here is one way to convert date into Calendar objects
        // Date format validation is considered to be done mostly by front end techniques
        List<Integer> start = new ArrayList<Integer>();
        List<Integer> end = new ArrayList<Integer>();
        String startDateStr = args[0];
        String endDateStr = args[1];
        String locale = args[2];
        if (startDateStr.split(DATE_SPLITTER).length != 3 || endDateStr.split(DATE_SPLITTER).length != 3) {
            throw new Exception("Wrong date format");
        }
        for (String str : startDateStr.split(DATE_SPLITTER)) {
            start.add(Integer.parseInt(str));
        }
        for (String str : endDateStr.split(DATE_SPLITTER)) {
            end.add(Integer.parseInt(str));
        }
        Calendar periodStartDate = new GregorianCalendar(start.get(2), start.get(1)-1, start.get(0));
        Calendar periodEndDate = new GregorianCalendar(end.get(2), end.get(1)-1, end.get(0));


        // set period start and end date
        // notice that in months january is 0 and december is 11
        //Calendar periodStartDate = new GregorianCalendar(2020,11, 1);
        //Calendar periodEndDate = new GregorianCalendar(2021, 0, 3);
        // create new HolidayPlanner instance and print out results
        try {
            HolidayPlanner hp = new HolidayPlanner(periodStartDate, periodEndDate, "fi");
            System.out.println("Period: " + hp.getPeriodStartAsString() + " - " + hp.getPeriodEndAsString());
            System.out.println("National holidays in " + hp.getLocale().getLanguage() + ": " + hp.getNationalHolidaysAsString());
            System.out.println("Period days: "+ hp.getPeriodLength());
            System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
