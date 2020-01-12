package io.jopen.orm.hbase.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.sql.Connection;

/**
 * @author maxuefeng
 * @see PhoenixConnectionPool
 * @since 2019/8/27
 */
public class PhoenixPoolConfig extends GenericObjectPoolConfig<Connection> {

    public PhoenixPoolConfig() {
        setMaxTotal(10);
        setMaxIdle(5);
        setMinIdle(2);
    }

    public PhoenixPoolConfig(int maxTotal, int maxIdle, int minIdle) {
        setMaxTotal(maxTotal);
        setMaxIdle(maxIdle);
        setMinIdle(minIdle);
    }

    public static PhoenixPoolConfig of() {
        return new PhoenixPoolConfig();
    }

}
