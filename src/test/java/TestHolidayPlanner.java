package fi.holidayplanner;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import java.util.List;
import java.util.Arrays;

public class TestHolidayPlanner {

    @Test
    public void testChristmasHoliday() throws Exception {
        HolidayPlanner hp = new HolidayPlanner("1.12.2020", "3.1.2021");
        assertEquals(26, hp.getHolidayPeriodConsumableDays());
    }

    @Test
    public void testSummerHoliday() throws Exception {
        HolidayPlanner hp = new HolidayPlanner("1.6.2020", "30.6.2020");
        assertEquals(25, hp.getHolidayPeriodConsumableDays());
    }

    // this tests exactly 50 days long calendar period
    @Test
    public void testMaxLength() throws Exception {
        HolidayPlanner hp = new HolidayPlanner("4.1.2021", "22.2.2021");
        assertEquals(42, hp.getHolidayPeriodConsumableDays());
    }

    // this tests exactly 1 day too long calendar period
    @Rule
    public ExpectedException tooLongThrown = ExpectedException.none();
    @Test
    public void testTooLongHolidayPeriod() throws Exception {
        tooLongThrown.expect(Exception.class);
        tooLongThrown.expectMessage("Too long holiday period");
        HolidayPlanner hp = new HolidayPlanner("4.1.2021", "23.2.2021");
    }

    @Rule
    public ExpectedException invalidPeriodThrown = ExpectedException.none();
    @Test
    public void testInvalidHolidayPeriod() throws Exception {
        invalidPeriodThrown.expect(Exception.class);
        invalidPeriodThrown.expectMessage("Invalid holiday period");
        HolidayPlanner hp = new HolidayPlanner("15.3.2020", "1.5.2020");
    }

    @Rule
    public ExpectedException notChronologicalThrown = ExpectedException.none();
    @Test
    public void testNotChronologicalHolidayPeriod() throws Exception {
        notChronologicalThrown.expect(Exception.class);
        notChronologicalThrown.expectMessage("Holiday period not in chronological order");
        HolidayPlanner hp = new HolidayPlanner("15.6.2020", "1.6.2020");
    }

    @Rule
    public ExpectedException invalidDateThrown = ExpectedException.none();
    @Test
    public void testInvalidDateFormat() throws Exception {
        invalidDateThrown.expect(Exception.class);
        invalidDateThrown.expectMessage("Invalid date format");
        HolidayPlanner hp = new HolidayPlanner("15.6.2020", "1/6/2020");
    }

    // test for some other country national holidays
    @Test
    public void testHolidayPeriodWithRussiaNationalHolidays() throws Exception {
        List<String> nationalHolidaysRus = Arrays.asList(new String[]{"1.1.2021", "2.1.2021", "3.1.2021", "4.1.2021", "5.1.2021", "6.1.2021", "7.1.2021", "8.1.2021", "9.1.2021", "10.1.2021", "21.2.2021", "22.2.2021", "23.2.2021", "6.3.2021", "7.3.2021", "8.3.2021", "1.5.2021", "2.5.2021", "3.5.2021", "8.5.2021", "9.5.2021", "10.5.2021", "12.6.2021", "13.6.2021", "14.6.2021", "4.11.2021", "5.11.2021", "6.11.2021", "7.11.2021", "31.12.2021"});
        HolidayPlanner hp = new HolidayPlanner("1.1.2021", "31.1.2021");
        hp.setNationalHolidays(nationalHolidaysRus);
        assertEquals(18, hp.getHolidayPeriodConsumableDays());
    }
}