package io.jopen.orm.hbase.hbase.util;

import com.google.code.morphia.utils.Assert;
import io.jopen.orm.hbase.pool.PhoenixConnectionConfig;
import io.jopen.orm.hbase.pool.PhoenixConnectionFactory;
import io.jopen.orm.hbase.pool.PhoenixConnectionPool;
import io.jopen.orm.hbase.pool.PhoenixPoolConfig;

import java.sql.Connection;

/**
 * Manages the phoenix connections based on JDBC driver
 *
 * @author maxuefeng
 */
public class PhoenixConnectionManager {

    private final static String DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private PhoenixConnectionPool connectionPool;

    private PhoenixConnectionManager(String url) {
        PhoenixConnectionConfig connectionConfig = PhoenixConnectionConfig.of(url, DRIVER);
        PhoenixPoolConfig poolConfig = PhoenixPoolConfig.of();
        PhoenixConnectionFactory factory = new PhoenixConnectionFactory(connectionConfig);
        connectionPool = new PhoenixConnectionPool(poolConfig, factory);
    }

    public static PhoenixConnectionManager defaultIns(String url) {
        return new PhoenixConnectionManager(url);
    }

    public Connection getConnection() {
        Assert.isNotNull(connectionPool, "connectionPool is null");
        return connectionPool.getConnection();
    }

    public void returnConnection(Connection conn) {
        Assert.isNotNull(conn, "connection is not null");
        Assert.isNotNull(connectionPool, "connectionPool is null");
        this.connectionPool.returnConnection(conn);
    }
}
