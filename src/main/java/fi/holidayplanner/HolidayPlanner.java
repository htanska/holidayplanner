package fi.holidayplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.lang.Exception;

/*
 * @author Henri Tanskanen
 * htanska@gmail.com
 */
public class HolidayPlanner {

    private Calendar startDate, endDate, CAL;
    private  List<String> NATIONAL_HOLIDAYS = Arrays.asList(new String[]{"1.1.2020" ,"6.1.2020" ,"10.4.2020" ,"13.4.2020" ,"1.5.2020" ,"21.5.2020" ,"19.6.2020" ,"24.12.2020" ,"25.12.2020" ,"1.1.2021" ,"6.1.2021" ,"2.4.2021" ,"5.4.2021" ,"13.5.2021" ,"20.6.2021" ,"6.12.2021" ,"24.12.2021"});

    private final SimpleDateFormat SDF = new SimpleDateFormat("d.M.yyyy");
    private final int MAX_TIME_SPAN = 50;
    private final String DATE_SPLITTER = "\\.";

    // Constructor
    public HolidayPlanner(String startDate, String endDate) throws Exception {

        if (startDate.split(DATE_SPLITTER).length != 3 || endDate.split(DATE_SPLITTER).length != 3) {
            throw new Exception("Invalid date format");
        }
        String []periodStart = startDate.trim().split(DATE_SPLITTER);
        String []periodEnd = endDate.trim().split(DATE_SPLITTER);

        this.CAL = GregorianCalendar.getInstance();
        this.startDate = GregorianCalendar.getInstance();
        this.endDate = GregorianCalendar.getInstance();
        this.startDate.set(Integer.parseInt(periodStart[2]), Integer.parseInt(periodStart[1])-1, Integer.parseInt(periodStart[0]));
        this.endDate.set(Integer.parseInt(periodEnd[2]), Integer.parseInt(periodEnd[1])-1, Integer.parseInt(periodEnd[0]));

        // Class won't accept time span if:
        // - dates are not in chronological order
        // - length of the time span is more 50 days
        // - time span doesn't fit within the current holiday period
        if (!isChronologicalOrder(this.startDate, this.endDate)) {
            throw new Exception("Holiday period not in chronological order");
        }
        if (!isUnderMaxLength(this.startDate, this.endDate)) {
            throw new Exception("Too long holiday period");
        }
        if (!isOnHolidayPeriod(this.startDate, this.endDate)) {
            throw new Exception("Invalid holiday period");
        }
    }

    //
    public int getHolidayPeriodConsumableDays() {
        int holidayCount = 0;
        int maxDays = 0;
        Calendar cal = new GregorianCalendar(startDate.get(CAL.YEAR), startDate.get(CAL.MONTH), startDate.get(CAL.DATE));
        do {
            if (cal.get(CAL.DAY_OF_WEEK) != CAL.SUNDAY && !this.NATIONAL_HOLIDAYS.contains(SDF.format(cal.getTime()))) {
                holidayCount++;
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
            maxDays++; // for not to loop forever which should not ever happen because dates have been checked
        } while ((cal.get(CAL.YEAR) != endDate.get(CAL.YEAR) || cal.get(CAL.MONTH) != endDate.get(CAL.MONTH) || cal.get(CAL.DATE) != endDate.get(CAL.DATE)) && maxDays < MAX_TIME_SPAN);
        // last day is missing, so check and add last one if is consumable day
        if (endDate.get(CAL.DAY_OF_WEEK) != CAL.SUNDAY && !this.NATIONAL_HOLIDAYS.contains(SDF.format(cal.getTime()))) {
            holidayCount++;
        }
        return holidayCount;
    }

    //
    private boolean isChronologicalOrder(Calendar startDate, Calendar endDate) {
        return endDate.after(startDate);
    }

    // For checking valid holiday period we first compare years and if equal then check if start is before april then end cannot be april or after.
    private boolean isOnHolidayPeriod(Calendar startDate, Calendar endDate) {
        // if days are on same year
        if (startDate.get(CAL.YEAR) == endDate.get(CAL.YEAR)) {
            // if months do not pass rule
            if (startDate.get(CAL.MONTH) < CAL.APRIL && endDate.get(CAL.MONTH) >= CAL.APRIL) {
                return false;
            }
        }
        return true;
    }

    //
    private boolean isUnderMaxLength(Calendar startDate, Calendar endDate) {
        Calendar test = new GregorianCalendar(startDate.get(CAL.YEAR), startDate.get(CAL.MONTH), startDate.get(CAL.DATE));
        test.add(CAL.DAY_OF_YEAR, MAX_TIME_SPAN);
        return !test.before(endDate);
    }

    public void setNationalHolidays(List<String> nationalHolidays) {
        this.NATIONAL_HOLIDAYS = nationalHolidays;
    }

    //
    public static void main(String []args) throws Exception {
        HolidayPlanner hp = new HolidayPlanner(args[0], args[1]);
        System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
    }

}
