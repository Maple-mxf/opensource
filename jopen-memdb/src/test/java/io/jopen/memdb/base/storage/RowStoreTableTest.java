package io.jopen.memdb.base.storage;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.jopen.core.common.text.Worker;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/24
 */
public class RowStoreTableTest {

    private RowStoreTable rowStoreTable;
    private Database database = new Database("default");

    @Before
    public void beforeCreateTable() {
        ColumnType idColumn = new ColumnType(String.class, "id", true);
        ColumnType nameColumn = new ColumnType(String.class, "name");
        rowStoreTable = new RowStoreTable(database, "student", ImmutableList.of(idColumn, nameColumn));
    }

    /**
     * @see RowStoreTable
     * @see com.google.common.collect.Table.Cell
     */
    @Test
    public void testStorageData() {

        // 保存数据
        String id = Worker.id();
        Row<String, Object> row = Row.row(Id.of(ImmutableMap.of("id", id)), ImmutableBiMap.of("id", id, "name", "Jack"));
        rowStoreTable.save(row);

        System.err.println(rowStoreTable);
    }

}
