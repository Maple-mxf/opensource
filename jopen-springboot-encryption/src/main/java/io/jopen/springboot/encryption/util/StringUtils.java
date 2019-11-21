package io.jopen.springboot.encryption.util;

/**
 * <p>字符串处理工具类</p>
 * @author licoy.cn
 * @version 2018/9/6
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

}
