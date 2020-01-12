package io.jopen.orm.hbase.pool;

/**
 * @author maxuefeng
 * @since 2019/8/27
 */
public class PhoenixConnectionConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public PhoenixConnectionConfig(String url, String driverClassName) {
        this.url = url;
        this.driverClassName = driverClassName;
    }

    public PhoenixConnectionConfig(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public static PhoenixConnectionConfig of(String url, String driverClassName) {
        return of(url, null, null, driverClassName);
    }

    public static PhoenixConnectionConfig of(String url, String username, String password, String driverClassName) {
        return new PhoenixConnectionConfig(url, username, password, driverClassName);
    }
}
