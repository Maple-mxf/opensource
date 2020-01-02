package io.jopen.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author maxuefeng
 * @since 2019/11/26
 */
public class DBConnectionPool {

    private static volatile DBConnectionPool dbConnection;
    private ComboPooledDataSource cpds;

    /**
     * password.txt
     * 在构造函数初始化的时候获取数据库连接
     */
    private DBConnectionPool() {
        try {
            Properties properties = new Properties();
            InputStream is = DBConnectionPool.class.getClassLoader().getResourceAsStream("mysql.properties");
            properties.load(is);
            String driverClassName = properties.getProperty("mysql.pool.jdbc.driverClass");
            String url = properties.getProperty("mysql.jdbc.url");
            String username = properties.getProperty("mysql.jdbc.username");
            String password = properties.getProperty("mysql.jdbc.password");

            cpds = new ComboPooledDataSource();

            cpds.setDriverClass(driverClassName);
            cpds.setJdbcUrl(url);
            cpds.setUser(username);

            cpds.setPassword(password);

            cpds.setInitialPoolSize(3);
            cpds.setMaxPoolSize(10);
            cpds.setAcquireIncrement(1);
            cpds.setIdleConnectionTestPeriod(60);
            cpds.setMaxIdleTime(3000);

            cpds.setTestConnectionOnCheckout(true);

            cpds.setTestConnectionOnCheckin(true);
            cpds.setAcquireRetryAttempts(30);
            cpds.setAcquireRetryDelay(1000);
            cpds.setBreakAfterAcquireFailure(true);
        } catch (IOException | PropertyVetoException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据库连接对象，单例
     *
     * @return
     */
    static DBConnectionPool getInstance() {
        if (dbConnection == null) {
            synchronized (DBConnectionPool.class) {
                if (dbConnection == null) {
                    dbConnection = new DBConnectionPool();
                }
            }
        }
        return dbConnection;
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    final synchronized Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    /**
     * finalize()方法是在垃圾收集器删除对象之前对这个对象调用的。
     *
     * @throws Throwable
     */
    protected void finalize() throws Throwable {
        DataSources.destroy(cpds);
        super.finalize();
    }
}
