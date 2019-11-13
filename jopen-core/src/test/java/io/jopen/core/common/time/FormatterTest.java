package io.jopen.core.common.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @author maxuefeng
 */
public class FormatterTest {

    @Test
    public void testFormatLocalDateTime() {
        System.err.println(Formatter.f(LocalDateTimeHelper.toLocalDateTime(new Date().getTime()), Formatter.P.P6));
        System.err.println(DateFormatUtils.format(1565366400000L,"yyyy-MM-dd"));
    }
}
