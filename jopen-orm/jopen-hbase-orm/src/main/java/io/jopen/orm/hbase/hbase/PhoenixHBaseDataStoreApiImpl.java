package io.jopen.orm.hbase.hbase;

import com.google.code.morphia.utils.Assert;
import com.google.common.base.Preconditions;
import io.jopen.orm.hbase.api.DataStoreApi;
import io.jopen.orm.hbase.hbase.query.PhoenixHBaseQueryExecutor;
import io.jopen.orm.hbase.hbase.util.PhoenixConnectionManager;
import io.jopen.orm.hbase.query.QuerySelect;
import io.jopen.orm.hbase.query.builder.QueryBuilder;
import io.jopen.orm.hbase.query.builder.QueryUpdateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoenixHBaseDataStoreApiImpl implements DataStoreApi {

    private final PhoenixHBaseQueryExecutor queryExecutor;
    private final String connectionUrl;
    private static final Logger logger = LoggerFactory.getLogger(PhoenixHBaseDataStoreApiImpl.class);
    private final PhoenixConnectionManager connectionManager;

    public PhoenixHBaseDataStoreApiImpl(
            final String connectionUrl,
            final PhoenixHBaseQueryExecutor queryExecutor) throws Exception {
        this(connectionUrl, queryExecutor, false);
    }

    public PhoenixHBaseDataStoreApiImpl(final String connectionUrl,
                                        final PhoenixHBaseQueryExecutor queryExecutor,
                                        final boolean testConnection) throws Exception {

        this.connectionUrl = connectionUrl;
        this.queryExecutor = Preconditions.checkNotNull(queryExecutor);
        connectionManager = PhoenixConnectionManager.defaultIns(this.connectionUrl);

        // Below code will ensure that connection string is valid, if not will stop the context loading
        if (testConnection) {
            Connection conn = connectionManager.getConnection();
            Assert.isNotNull(conn, String.format("unable to create phoenix connection with given url :%s", connectionUrl));
            returnConnectionSafe(conn);
        }
    }

    @Override
    public <T> T save(T entity) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            T returnEntity = queryExecutor.save(entity, conn);
            conn.commit();
            return returnEntity;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    private void returnConnectionSafe(Connection conn) {
        connectionManager.returnConnection(conn);
    }


    @Override
    public <T> Iterable<T> save(Iterable<T> entities) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            Iterable<T> results = queryExecutor.save(entities, conn);
            conn.commit();
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    @Override
    public <T> int[] saveBatch(Iterable<T> entities) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            int[] results = queryExecutor.saveBatch(entities, conn);
            conn.commit();
            return results;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    @Override
    public <T, R> Iterable<R> findAll(QuerySelect<T, R> query) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            return queryExecutor.find(query, conn);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    @Override
    public <T, R> List<R> findList(QuerySelect<T, R> query) {

        Iterable<R> iterable = findAll(query);
        List<R> returnValue = new ArrayList<>();
        while (iterable.iterator().hasNext()) {
            returnValue.add(iterable.iterator().next());
        }
        return returnValue;
    }

    @Override
    public <T, R> Set<R> findSet(QuerySelect<T, R> query) {
        return new HashSet<>(findList(query));
    }

    @Override
    public <T, R> R findOne(QuerySelect<T, R> query) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            return queryExecutor.findOne(query, conn);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    public <T> Iterable<T> findAllEntities(String key, Class<T> clz, String[] projection) throws Exception {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            QueryBuilder<T, T> builder = new QueryBuilder<>(clz, clz);
            builder.setReturnFields(projection);
            QuerySelect<T, T> query = builder.build();
            return queryExecutor.find(query, conn);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

    @Override
    public <T> T save(T entity, List<String> selectedFields) {
        Connection conn = null;
        try {
            conn = connectionManager.getConnection();
            QueryUpdateBuilder updateBuilder = QueryUpdateBuilder.builderFor(entity).update(selectedFields);
            T returnEntity = (T) queryExecutor.save(updateBuilder.build(), conn);
            conn.commit();
            return returnEntity;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            returnConnectionSafe(conn);
        }
    }

}
