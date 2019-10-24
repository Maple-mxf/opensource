package io.jopen.memdb.base.storage;

import com.google.common.collect.ImmutableSet;
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
    private void beforeCreateTable() {
        ColumnType idColumn = new ColumnType(String.class, "id", true);
        ColumnType nameColumn = new ColumnType(String.class, "name");
        rowStoreTable = new RowStoreTable(database, "student", ImmutableSet.of(idColumn, nameColumn));
    }

    /**
     * @see RowStoreTable
     * @see com.google.common.collect.Table.Cell
     */
    @Test
    public void testStorageData() {

        // 保存数据
        rowStoreTable.save();
    }

}
