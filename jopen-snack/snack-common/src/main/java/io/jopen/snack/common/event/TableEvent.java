package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.protol.RpcData;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class TableEvent implements SnackApplicationEvent {

    private TableInfo tableInfo;

    private TableInfo targetTableInfo;

    private RpcData.C2S.TableOperation tableOperation;

    private DatabaseInfo databaseInfo;

    public TableInfo getTargetTableInfo() {
        return targetTableInfo;
    }

    public void setTargetTableInfo(TableInfo targetTableInfo) {
        this.targetTableInfo = targetTableInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public RpcData.C2S.TableOperation getTableOperation() {
        return tableOperation;
    }

    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public void setTableOperation(RpcData.C2S.TableOperation tableOperation) {
        this.tableOperation = tableOperation;
    }

    TableEvent(TableInfo tableInfo, RpcData.C2S.TableOperation tableOperation) {
        this.tableInfo = tableInfo;
        this.tableOperation = tableOperation;
    }

    public static class Create extends TableEvent {
        public Create(TableInfo tableInfo, RpcData.C2S.TableOperation tableOperation) {
            super(tableInfo, tableOperation);
        }
    }

    public static class Drop extends TableEvent {
        public Drop(TableInfo tableInfo, RpcData.C2S.TableOperation tableOperation) {
            super(tableInfo, tableOperation);
        }
    }

    public static class Modify extends TableEvent {
        public Modify(TableInfo tableInfo, RpcData.C2S.TableOperation tableOperation) {
            super(tableInfo, tableOperation);
        }
    }
}
