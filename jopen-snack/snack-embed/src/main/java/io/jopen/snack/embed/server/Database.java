package io.jopen.snack.embed.server;

import com.google.common.base.Preconditions;
import io.jopen.snack.common.annotation.Util;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/22
 */
public final
class Database implements Serializable {

    private String dbName;

    private transient Translator translator = new Translator();

    private ConcurrentHashMap<String, RowStoreTable> rowStoreTables = new ConcurrentHashMap<>();

    public RowStoreTable getRowStoreTable(String tableName) {
        Preconditions.checkNotNull(tableName);
        return this.rowStoreTables.get(tableName);
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

    public final <T> RowStoreTable createTable(@NonNull Class clazz) {
        // 创建表格的先决条件  至少存在一个主键
        RowStoreTable rowStoreTable = translator.mapJavaBeanToTable(clazz, this);
        this.rowStoreTables.put(Util.entityVal(clazz), rowStoreTable);
        return rowStoreTable;
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
    }
}
