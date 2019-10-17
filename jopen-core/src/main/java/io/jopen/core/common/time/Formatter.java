package io.jopen.core.common.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author maxuefeng
 * @see LocalDateTime
 * @see org.apache.commons.lang3.time.DateFormatUtils
 * @see org.apache.commons.lang3.time.DateUtils
 */
public class Formatter {

    public interface P {

        String P1 = "yyyyMMddHHmmssSSS";

        String P2_0 = "yyyy-MM-dd HH:mm:ss";

        String P2_1 = "yyyy-MM-dd HH:mm";

        String P3 = "yyyy年MM月dd HH:mm:ss";

        String P4 = "yyyy年MM月dd HH时mm分ss秒";

        String P5 = "yyyy年MM月dd HH时mm分ss秒SSS毫秒";

        String P6 = "yyyy年MM月dd日";

        String P7 = "yyyy年MM月dd号";

        // 几天前  // 几小时前  // 几分钟前
        String YESTERDAY = "昨天";

        String FEW_DAYS_AGO = "#天前";
    }

    public static String now(String P) {
        return f(LocalDateTime.now(), P);
    }


    public final static String P6 = "";

    public static String f(Date date, String P) {
        return f(date.getTime(), P);
    }

    public static String f(long timestamp, String P) {
        return f(LocalDateTimeHelper.toLocalDateTime(timestamp), P);
    }

    public static String f(LocalDateTime dateTime, String P) {
        return dateTime.format(DateTimeFormatter.ofPattern(P));
    }
}
