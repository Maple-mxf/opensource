package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class TableEvent implements SnackApplicationEvent {

    private TableInfo tableInfo;

    private DatabaseInfo databaseInfo;

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    TableEvent(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        this.databaseInfo = databaseInfo;
        this.tableInfo = tableInfo;
    }

    public static class Create extends TableEvent {
        public Create(DatabaseInfo databaseInfo, TableInfo tableInfo) {
            super(databaseInfo, tableInfo);
        }
    }

    public static class Drop extends TableEvent {
        public Drop(DatabaseInfo databaseInfo, TableInfo tableInfo) {
            super(databaseInfo, tableInfo);
        }
    }

    public static class Modify extends TableEvent {
        private TableInfo targetTableInfo;

        public Modify(DatabaseInfo databaseInfo, TableInfo tableInfo, TableInfo targetTableInfo) {
            super(databaseInfo, tableInfo);
            this.targetTableInfo = targetTableInfo;
        }

        public TableInfo getTargetTableInfo() {
            return targetTableInfo;
        }

        public void setTargetTableInfo(TableInfo targetTableInfo) {
            this.targetTableInfo = targetTableInfo;
        }
    }
}
