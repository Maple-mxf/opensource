package io.jopen.memdb.base.storage;

import com.google.common.base.Joiner;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@link JavaModelTable}
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class RowStoreTable {

    // cells
    private final Table<Id, String, Object> cells = Tables.newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

    // table Name
    private String tableName;

    // 列的属性
    private Set<ColumnType> columnTypes;

    // database
    private transient Database database;

    RowStoreTable(Database database, String tableName, Set<ColumnType> columnTypes) {
        this.database = database;
        this.tableName = tableName;
        this.columnTypes = columnTypes;
    }


    public Table<Id, String, Object> getCells() {
        return cells;
    }

    public String getTableName() {
        return tableName;
    }


    public Database getDatabase() {
        return database;
    }

    @Override
    public String toString() {

        StringBuilder rowStoreTable = new StringBuilder("RowStoreTable");
        rowStoreTable.append(this.tableName).append("\n");

        // 拼接列名
        String columnNames = Joiner.on("\t\t\t").join(columnTypes.parallelStream().map(ColumnType::getColumnName).collect(Collectors.toSet()));
        rowStoreTable.append(columnNames);
        rowStoreTable.append("\n");

        // 拼接行数据  rowKeySet属于主键
        // 获取所有Id
        Set<Id> ids = cells.rowKeySet();

        for (Id id : ids) {
            for (ColumnType columnType : this.columnTypes) {
                Object cell = cells.get(id, columnType.getColumnName());
                rowStoreTable.append(cell);
                rowStoreTable.append("\t\t\t");
            }
            rowStoreTable.append("\n");
        }
        return rowStoreTable.toString();
    }
}
