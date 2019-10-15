package core.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author maxuefeng
 * @see Properties
 */
public class PropertiesHelper {

    private Properties properties;

    private PropertiesHelper(InputStream inputStream) throws IOException {
        this.properties = new Properties();
        this.properties.load(inputStream);
    }

    public static PropertiesHelper of(InputStream inputStream) throws IOException {
        return new PropertiesHelper(inputStream);
    }

    public Object getByKey(String key) {
        return this.properties.getOrDefault(key, null);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }
}
