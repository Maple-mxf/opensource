package core.common.time;

import java.util.Date;

/**
 * @author maxuefeng
 * @see Date
 */
public class DateHelper {

    public static Date of() {
        return new Date();
    }

    public static long mills() {
        return of().getTime();
    }
}
