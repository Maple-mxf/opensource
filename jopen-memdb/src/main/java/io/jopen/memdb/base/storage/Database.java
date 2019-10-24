package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link JavaModelTable}
 *
 * @author maxuefeng
 * @since 2019/10/22
 */
final
class Database implements Serializable {

    private String dbName;

    @Deprecated
    public <T> JavaModelTable<T> getTable(String tableName) {
        Preconditions.checkNotNull(tableName);
        return tables.get(tableName);
    }

    ConcurrentHashMap<String, JavaModelTable> tables = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, RowStoreTable> rowStoreTables = new ConcurrentHashMap<>();

    public RowStoreTable getRowStoreTable(String tableName) {
        Preconditions.checkNotNull(tableName);
        return this.rowStoreTables.get(tableName);
    }

    @Deprecated
    public ConcurrentHashMap<String, JavaModelTable> showTables() {
        return this.tables;
    }

    public String getDbName() {
        return dbName;
    }

    Database(String dbName) {
        this.dbName = dbName;
    }
}
