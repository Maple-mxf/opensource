package io.jopen.snack.common.storage;

import com.google.common.base.Preconditions;
import io.jopen.snack.common.ColumnInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.annotation.Util;
import io.jopen.snack.common.exception.NoPrimaryKeyException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@link RowStoreTable}
 * {@link DBManagement}
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

    public RowStoreTable getRowStoreTable(TableInfo tableInfo) {
        return this.getRowStoreTable(tableInfo.getName());
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

    public final RowStoreTable createTable(TableInfo tableInfo) {
        List<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();


        // 检测是否存在主键
        SnackExceptionUtil.check(input -> {
            assert input != null;
            return input.stream().noneMatch(ColumnInfo::getPrimaryKey);
        }, columnInfoList, NoPrimaryKeyException.class, String.format("create table must has primary key，table info [ %s ] ", tableInfo));

        // 创建表格
        RowStoreTable rowStoreTable = new RowStoreTable(this, tableInfo.getName(), columnInfoList);

        // 保证原子性
        synchronized (this) {
            // 检测重复性
            SnackExceptionUtil.check(input -> Database.this.rowStoreTables.entrySet().parallelStream().anyMatch(entry -> entry.getKey().equals(input.getName())),
                    tableInfo,
                    SnackRuntimeException.class,
                    String.format("table is exist, table info [ %s ] ", tableInfo));
            this.rowStoreTables.put(tableInfo.getName(), rowStoreTable);
        }
        return rowStoreTable;
    }

    @Deprecated
    public final synchronized RowStoreTable createTable(@NonNull Class clazz) {
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

    public final synchronized boolean dropTable(TableInfo tableInfo) {
        RowStoreTable storeTable = this.rowStoreTables.remove(tableInfo.getName());
        return storeTable != null;
    }

    public final Set<String> showTables() {
        Set<String> tables = this.rowStoreTables.entrySet().parallelStream().map(Map.Entry::getKey).collect(Collectors.toSet());
        return tables;
    }

    /**
     * 如果不存在指定table则需要主动创建
     *
     * @param tableInfo
     * @return
     */
    @NonNull
    public final RowStoreTable securityGetTable(@NonNull TableInfo tableInfo) {
        try {
            return this.createTable(tableInfo);
        } catch (SnackRuntimeException ex) {
            ex.printStackTrace();
            return this.getRowStoreTable(tableInfo);
        }
    }
}
