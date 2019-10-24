package io.jopen.memdb.base.storage;

import com.google.common.base.Joiner;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link JavaModelTable}
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class RowStoreTable {

    // cells
    final Table<Id, String, Object> cells = Tables.newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

    // table Name
    private String tableName;

    // 列名和类型
    private Map<String, Class> columnTypes = new ConcurrentHashMap<>();

    // database
    private Database database;


    public Table<Id, String, Object> getCells() {
        return cells;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String toString() {

        StringBuilder rowStoreTable = new StringBuilder("RowStoreTable");
        rowStoreTable.append(this.tableName).append("\n");

        // 拼接列名
        String columnNames = Joiner.on("                ").join(columnTypes.keySet());
        rowStoreTable.append(columnNames);
        rowStoreTable.append("\n");

        // 拼接行数据  rowKeySet属于主键
        // Set<Object> rowKeySet = cells.rowKeySet();

        return rowStoreTable.toString();
    }
}
