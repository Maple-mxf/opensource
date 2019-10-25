package io.jopen.memdb.base.storage.server;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * 数据内存式存储测试通过
 * {@link RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
public class RowStoreTableStorageTest {

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
        String id = UUID.randomUUID().toString();
        Row row = Row.row(Id.of(ImmutableMap.of("id", id)), ImmutableBiMap.of("id", id, "name", "Jack"));
        rowStoreTable.save(row);

        System.err.println(rowStoreTable);
    }

}
