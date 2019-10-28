package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.protol.RpcData;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class RowEvent implements SnackApplicationEvent {

    protected DatabaseInfo databaseInfo;

    protected TableInfo tableInfo;

    protected RpcData.C2S.RowOperation rowOperation;

    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public RpcData.C2S.RowOperation getRowOperation() {
        return rowOperation;
    }

    public void setRowOperation(RpcData.C2S.RowOperation rowOperation) {
        this.rowOperation = rowOperation;
    }

    public static class Insert extends RowEvent {
        public Insert(DatabaseInfo databaseInfo,TableInfo tableInfo){
            this.databaseInfo = databaseInfo;
            this.tableInfo = tableInfo;
        }
    }

    public static class Delete extends RowEvent {
    }

    public static class Query extends RowEvent {
    }

    public static class Update extends RowEvent {
    }


}
