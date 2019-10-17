package io.jopen.core.common.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author maxuefeng
 * @see LocalTime
 * @see LocalDateTime
 * @see LocalDate
 */
public class LocalDateTest {

    @Test
    public void testSimpleAPI() {

        // LocalDate.now()

        // Clock.offset()

        LocalDate now1 = LocalDate.of(2019, 4, 3);

        System.err.println(now1.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.CHINA)));

        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: ClockHourOfAmPm
        // System.err.println(now1.get(ChronoField.CLOCK_HOUR_OF_AMPM));


        //
        LocalDateTime now2 = now1.atTime(LocalTime.now());

        //
        LocalTime toLocalTime = now2.toLocalTime();

        //
        LocalDate toLocalDate = now2.toLocalDate();

        // now1.get()

        // now1.format()

        // LocalDateTime.now().

        // LocalTime.now()

        Locale chinaLocal = Locale.CHINA;

        // rs: CN
        System.err.println(chinaLocal.getCountry());
    }

    @Test
    public void testGetTodayStart(){
        LocalDateTime todayStart = LocalDateTimeHelper.getTodayStart();

        String rs = Formatter.f(todayStart, Formatter.P.P4);

        System.err.println(rs);
    }
}
