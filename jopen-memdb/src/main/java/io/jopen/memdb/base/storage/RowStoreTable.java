package io.jopen.memdb.base.storage;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@link JavaModelTable}
 *
 * @author maxuefeng
 * @since 2019/10/24
 * TODO   为了方便测试  当前类暂时声明为public类型的
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

    // create table Precondition

    // save cell data Precondition  主键为空或者不唯一则返回false
    private Predicate<Table.Cell<Id, String, Object>> saveCellPreconditionId = cell -> {

        if (cell == null) {
            return false;
        }

        // 获取主键
        Id primaryKey = cell.getRowKey();

        if (primaryKey == null || primaryKey.size() == 0 || primaryKey.isNull()) {
            return false;
        }

        // 检测主键
        List<ColumnType> primaryKeyColumnTypes = RowStoreTable.this.columnTypes.parallelStream()
                .filter(ColumnType::getPrimaryKey).collect(Collectors.toList());

        // 检测大小
        if (primaryKeyColumnTypes.size() != primaryKey.size()) {
            return false;
        }

        // 检测具体的值是否为空
        /*primaryKeyColumnTypes.parallelStream().filter(pct->{
            cell.getValue()
        })*/

        return false;
    };


    /**
     * save data to table
     *
     * @see com.google.common.collect.Table.Cell#put(Object, Object, Object)
     */
    public void save(Table.Row<Id, String, Object> cell) {
        // before save data check data is complete
        this.cells.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
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
