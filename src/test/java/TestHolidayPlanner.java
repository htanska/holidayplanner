package fi.holidayplanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

public class TestHolidayPlanner {

    @Test
    public void testChristmasHoliday() throws Exception {
        Calendar start = new GregorianCalendar(2020, Calendar.DECEMBER, 1);
        Calendar end = new GregorianCalendar(2021, Calendar.JANUARY, 3);
        HolidayPlanner hp = new HolidayPlanner(start, end, "fi");
        System.out.println("Christmas holiday test");
        System.out.println("Period: " + hp.getPeriodStartAsString() + " - " + hp.getPeriodEndAsString());
        System.out.println("National holidays in " + hp.getLocale().getLanguage() + ": " + hp.getNationalHolidaysAsString());
        System.out.println("Period days: "+ hp.getPeriodLength());
        System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
    }

    @Test
    public void testSummerHoliday() throws Exception {
        Calendar start = new GregorianCalendar(2020, Calendar.JUNE, 01);
        Calendar end = new GregorianCalendar(2020, Calendar.JUNE, 30);
        HolidayPlanner hp = new HolidayPlanner(start, end, "fi");
        System.out.println("Summer holiday test");
        System.out.println("Period: " + hp.getPeriodStartAsString() + " - " + hp.getPeriodEndAsString());
        System.out.println("National holidays in " + hp.getLocale().getLanguage() + ": " + hp.getNationalHolidaysAsString());
        System.out.println("Period days: "+ hp.getPeriodLength());
        System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
    }

    @Test
    public void testTooLongHolidayPeriod() throws Exception {
        Calendar start = new GregorianCalendar(2020, Calendar.JUNE, 01);
        Calendar end = new GregorianCalendar(2020, Calendar.AUGUST, 30);
        HolidayPlanner hp = new HolidayPlanner(start, end, "fi");
        System.out.println("Summer holiday test");
        System.out.println("Period: " + hp.getPeriodStartAsString() + " - " + hp.getPeriodEndAsString());
        System.out.println("National holidays in " + hp.getLocale().getLanguage() + ": " + hp.getNationalHolidaysAsString());
        System.out.println("Period days: "+ hp.getPeriodLength());
        System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
    }
    @Test
    public void testInvalidHolidayPeriod() throws Exception {
        Calendar start = new GregorianCalendar(2020, Calendar.MARCH, 15);
        Calendar end = new GregorianCalendar(2020, Calendar.MAY, 1);
        HolidayPlanner hp = new HolidayPlanner(start, end, "fi");
        System.out.println("Summer holiday test");
        System.out.println("Period: " + hp.getPeriodStartAsString() + " - " + hp.getPeriodEndAsString());
        System.out.println("National holidays in " + hp.getLocale().getLanguage() + ": " + hp.getNationalHolidaysAsString());
        System.out.println("Period days: "+ hp.getPeriodLength());
        System.out.println("Period consumable days: " + hp.getHolidayPeriodConsumableDays());
    }

}