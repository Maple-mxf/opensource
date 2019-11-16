package io.jopen.qconfig;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author maxuefeng
 * @since 2019/11/16
 */
public class QuickConfiguration {

    private Properties properties = new Properties();

    public QuickConfiguration(@NonNull Class<?> clazz, String propertiesFileName) throws IOException {
        InputStream is = clazz.getResourceAsStream(propertiesFileName);
        properties.load(is);


    }

    public static void main(String[] args) {
    }

}
