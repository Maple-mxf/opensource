package io.jopen.orm.hbase.hbase.util;

import com.google.common.base.Preconditions;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class PhoenixDateFormatUtil {

    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    
    public static final String formatDate(Date date) {
        Preconditions.checkNotNull(date, "date must not be null");
        String formattedDate = new SimpleDateFormat(TIMESTAMP_FORMAT).format(date);
        StringBuffer dateFormatBuilder = new StringBuffer("TO_DATE('");
        dateFormatBuilder.append(formattedDate).append("', 'yyyy-MM-dd HH:mm:ss z')");
        return dateFormatBuilder.toString();
    }
    
}
