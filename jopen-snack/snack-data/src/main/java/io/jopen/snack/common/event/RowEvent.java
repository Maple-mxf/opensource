package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.Id;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.protol.RpcData;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Set;

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
        private Collection<Row> rows;

        public Insert(@NonNull DatabaseInfo databaseInfo,
                      @NonNull TableInfo tableInfo,
                      @NonNull Collection<Row> rows) {
            this.databaseInfo = databaseInfo;
            this.tableInfo = tableInfo;
            this.rows = rows;
        }

        public Collection<Row> getRows() {
            return rows;
        }

        public void setRows(Collection<Row> rows) {
            this.rows = rows;
        }
    }

    public static class Delete extends RowEvent {
        private Set<Id> deleteIds;

        public Delete(@NonNull DatabaseInfo databaseInfo,
                      @NonNull TableInfo tableInfo,
                      @NonNull Set<Id> deleteIds) {
            this.databaseInfo = databaseInfo;
            this.tableInfo = tableInfo;
            this.deleteIds = deleteIds;
        }

        public Set<Id> getDeleteIds() {
            return deleteIds;
        }

        public void setDeleteIds(Set<Id> deleteIds) {
            this.deleteIds = deleteIds;
        }
    }

    public static class Update extends RowEvent {

        private Collection<Row> rows;

        public Update(@NonNull DatabaseInfo databaseInfo,
                      @NonNull TableInfo tableInfo,
                      @NonNull Collection<Row> rows) {
            this.databaseInfo = databaseInfo;
            this.tableInfo = tableInfo;
            this.rows = rows;
        }

        public Collection<Row> getRows() {
            return rows;
        }

        public void setRows(Collection<Row> rows) {
            this.rows = rows;
        }
    }


}
