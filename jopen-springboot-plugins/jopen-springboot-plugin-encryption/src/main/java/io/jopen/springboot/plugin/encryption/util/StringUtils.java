package io.jopen.springboot.plugin.encryption.util;

/**
 * <p>字符串处理工具类</p>
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

}
