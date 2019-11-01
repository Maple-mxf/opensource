package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.protol.RpcData;

import java.util.Collection;
import java.util.List;

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

        public Insert(DatabaseInfo databaseInfo, TableInfo tableInfo, Collection<Row> rows) {
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
        private List<IntermediateExpression<Row>> expressions;
        public Delete(DatabaseInfo databaseInfo, TableInfo tableInfo, List<IntermediateExpression<Row>> expressions) {
            this.databaseInfo = databaseInfo;
            this.tableInfo = tableInfo;
            this.expressions = expressions;
        }

        public List<IntermediateExpression<Row>> getExpressions() {
            return expressions;
        }

        public void setExpressions(List<IntermediateExpression<Row>> expressions) {
            this.expressions = expressions;
        }
    }

    public static class Query extends RowEvent {
    }

    public static class Update extends RowEvent {
    }


}
