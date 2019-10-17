package io.jopen.core.common.time;

import org.junit.Test;

import java.util.Date;

/**
 * @author maxuefeng
 */
public class FormatterTest {

    @Test
    public void testFormatLocalDateTime() {
        System.err.println(Formatter.f(LocalDateTimeHelper.toLocalDateTime(new Date().getTime()), Formatter.P.P6));
    }
}
