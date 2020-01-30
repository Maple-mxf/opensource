package io.jopen.springboot.plugin.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.time.LocalDateTime.now;

/**
 * @author maxuefeng
 * @see LocalDateTime
 */
public class LocalDateTimeUtil {

    /**
     * 时间转换到LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * LocalDateTime转换到时间戳
     *
     * @param date
     * @return
     */
    public static long toTimestamp(LocalDateTime date) {
        return date.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取今天的零点
     *
     * @return
     */
    public static LocalDateTime getTodayStart() {
        LocalDateTime now = now();
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
    }
}
