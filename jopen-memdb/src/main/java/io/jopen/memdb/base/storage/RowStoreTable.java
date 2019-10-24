package io.jopen.memdb.base.storage;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
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

    // table
    private final Table<Id, String, Object> table = Tables.newCustomTable(new ConcurrentHashMap<>(), ConcurrentHashMap::new);

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


    public Table<Id, String, Object> getTable() {
        return table;
    }

    public String getTableName() {
        return tableName;
    }


    public Database getDatabase() {
        return database;
    }

    // create table Precondition

    // save cell data Precondition  主键为空或者不唯一则返回false
    private Predicate<Row<String, Object>> saveCellPreconditionId = row -> {

        if (row == null) {
            return false;
        }

        // 获取主键
        Id primaryKey = row.getRowKey();

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
        Optional<ColumnType> optional = primaryKeyColumnTypes.parallelStream().filter(pct -> row.get(pct.getColumnName()) == null).findAny();
        return optional.isPresent();
    };

    // storage data  consumer the row
    private Consumer<Row<String, Object>> storageRow = row -> {
        Id rowKey = row.getRowKey();
        row.forEach((column, value) -> RowStoreTable.this.table.put(rowKey, column, value));
    };


    /**
     * save data to table
     *
     * @see Row#getRowKey()   storage data to the current table   in row
     * @see com.google.common.collect.Table.Cell#put(Object, Object, Object)
     */
    public void save(Row<String, Object> row) {
        // before save data check data is complete
        // this.table.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        boolean res = saveCellPreconditionId.apply(row);
        if (res) {
            throw new RuntimeException("save exception");
        }

        // put data
        storageRow.accept(row);
    }

    /**
     * save batch  row  data
     *
     * @param rows rows data
     */
    public void saveBatch(Collection<Row<String, Object>> rows) {
        Preconditions.checkNotNull(rows);
        rows.forEach(this::save);
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
        Set<Id> ids = table.rowKeySet();

        for (Id id : ids) {
            for (ColumnType columnType : this.columnTypes) {
                Object cell = table.get(id, columnType.getColumnName());
                rowStoreTable.append(cell);
                rowStoreTable.append("\t\t\t");
            }
            rowStoreTable.append("\n");
        }
        return rowStoreTable.toString();
    }

}
