package io.jopen.leopard.base.storage.deprecated;

import io.jopen.leopard.base.storage.server.RowStoreTable;

import java.io.Serializable;

/**
 * {@link JavaModelTable}
 * {@link RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/22
 */
@Deprecated
public final
class Database implements Serializable {
/*
    private String dbName;

    private transient Translator translator = new Translator();

    @Deprecated
    public <T> JavaModelTable<T> getTable(@NonNull String tableName) {
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

    public Database(String dbName) {
        this.dbName = dbName;
    }

    RowStoreTable dropTable(Class clazz) {
        String tableName = Util.entityVal(clazz);
        if (StringUtils.isNotBlank(tableName)) {
            if (!this.rowStoreTables.containsKey(tableName)) {
                throw new RuntimeException(String.format("table %s not exist", tableName));
            }
            return this.rowStoreTables.remove(tableName);
        }
        throw new RuntimeException(String.format("table %s not exist", tableName));
    }

    final <T> RowStoreTable createTable(Class clazz) {
        // 创建表格的先决条件  至少存在一个主键
        return translator.mapJavaBeanToTable(clazz, this);
    }

    final public RowStoreTable getTable(Class clazz) {
        String tableName = Util.entityVal(clazz);
        if (StringUtils.isNotBlank(tableName)) {
            if (!this.rowStoreTables.containsKey(tableName)) {
                throw new RuntimeException(String.format("table %s not exist", tableName));
            }
            return rowStoreTables.get(tableName);
        }
        throw new RuntimeException(String.format("table %s not exist", tableName));
    }*/
}