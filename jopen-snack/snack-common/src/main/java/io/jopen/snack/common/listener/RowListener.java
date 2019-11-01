package io.jopen.snack.common.listener;

import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.*;
import io.jopen.snack.common.event.RowEvent;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.storage.Database;
import io.jopen.snack.common.storage.RowStoreTable;
import io.jopen.snack.common.task.PersistenceTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class RowListener extends SnackApplicationListener {

    public static class Insert extends RowListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof RowEvent.Insert) {
                // 提交任务
                submit(new Task(() -> System.err.println("save data task executed completed"), new Callback(), event));
            }
        }

        private class Task extends PersistenceTask<Integer> {
            protected Task(@Nullable Runnable taskExecuteListener,
                           @NonNull FutureCallback<Integer> futureCallback,
                           @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Integer execute() {
                RowEvent.Insert insertEvent = (RowEvent.Insert) event;
                DatabaseInfo databaseInfo = insertEvent.getDatabaseInfo();
                TableInfo tableInfo = insertEvent.getTableInfo();

                Database database = Insert.super.dbManagement.getDatabase(databaseInfo);

                if (database == null) {
                    Insert.super.dbManagement.createDatabase(databaseInfo);

                    // 创建数据库
                    database = Insert.super.persistenceDatabase(databaseInfo);
                    if (database == null) {
                        return 0;
                    }
                }

                RowStoreTable rowStoreTable = database.getRowStoreTable(tableInfo);

                if (rowStoreTable == null) {
                    // 创建表格
                    rowStoreTable = Insert.super.persistenceTable(databaseInfo, tableInfo);
                    if (rowStoreTable == null) {
                        return 0;
                    }
                }
                return rowStoreTable.saveBatch(insertEvent.getRows());
            }
        }

        private class Callback implements FutureCallback<Integer> {
            @Override
            public void onSuccess(@Nullable Integer result) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }
    }

    public static class Delete extends RowListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof RowEvent.Delete) {
                submit(new Task(() -> System.err.println("save data task executed completed"), new Callback(), event));
            }
        }

        private class Task extends PersistenceTask<Integer> {
            protected Task(@Nullable Runnable taskExecuteListener,
                           @NonNull FutureCallback<Integer> futureCallback,
                           @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Integer execute() {
                RowEvent.Delete deleteEvent = (RowEvent.Delete) event;
                DatabaseInfo databaseInfo = deleteEvent.getDatabaseInfo();
                TableInfo tableInfo = deleteEvent.getTableInfo();


                Database database = Delete.super.dbManagement.getDatabase(databaseInfo);
                if (database == null) {
                    Delete.super.dbManagement.createDatabase(databaseInfo);
                    database = Delete.super.persistenceDatabase(databaseInfo);
                    if (database == null) {
                        return 0;
                    }
                }

                RowStoreTable table = database.getRowStoreTable(tableInfo);

                if (table == null) {
                    // 创建表格
                    createTable.test(database, tableInfo);
                    return 0;
                }
                List<IntermediateExpression<Row>> expressions = deleteEvent.getExpressions();
                List<Id> idList = table.delete(expressions);
                return idList.size();
            }
        }

        private class Callback implements FutureCallback<Integer> {
            @Override
            public void onSuccess(@Nullable Integer result) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }
    }

    public static class Update extends RowListener {
        
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }

    public static class Query extends RowListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

        }
    }
}
