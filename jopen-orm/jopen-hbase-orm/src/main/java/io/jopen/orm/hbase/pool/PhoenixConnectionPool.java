package io.jopen.orm.hbase.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;
import java.sql.Connection;
import java.util.Optional;

/**
 * @author maxuefeng
 * @since 2019/8/27
 */
public class PhoenixConnectionPool implements Closeable {

    private GenericObjectPool<Connection> internalPool;

    public PhoenixConnectionPool(GenericObjectPoolConfig<Connection> poolConfig, PooledObjectFactory<Connection> factory) {
        this.internalPool = new GenericObjectPool<>(factory, poolConfig);
    }

    /**
     * @return get a phoenix connection to sqlLine
     */
    public Connection getConnection() {

        Connection connection;
        try {
            connection = internalPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Could not get connection from the pool", e);
        }
        return connection;
    }

    /**
     * @param connection current connection push the pool
     */
    public void returnConnection(Connection connection) {
        internalPool.returnObject(connection);
    }


    @Override
    public void close() {
        this.closeInternalPool();
    }

    private void closeInternalPool() {
        try {
            Optional.ofNullable(internalPool).ifPresent(GenericObjectPool::close);
        } catch (Exception e) {
            throw new RuntimeException("Could not destroy the pool", e);
        }
    }
}
