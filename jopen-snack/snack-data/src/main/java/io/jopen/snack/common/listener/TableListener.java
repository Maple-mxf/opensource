package io.jopen.snack.common.listener;

import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.event.TableEvent;
import io.jopen.snack.common.storage.Database;
import io.jopen.snack.common.storage.RowStoreTable;
import io.jopen.snack.common.task.PersistenceTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * <p>{@link SnackApplicationListener}</p>
 *
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class TableListener extends SnackApplicationListener {

    public static class Create extends TableListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof TableEvent.Create) {
                // 提交创建数据库任务
                TableEvent createEvent = (TableEvent) event;
                PersistenceTask<Boolean> task = new TableListener.Create.Task(
                        () -> System.err.println("create database task completed "), // 监听器
                        new TableListener.Create.Callback(), // 回调函数
                        createEvent);
                submit(task);
            }
        }

        private class Task extends PersistenceTask<Boolean> {
            Task(@Nullable Runnable taskExecuteListener,
                 @NonNull FutureCallback<Boolean> futureCallback,
                 @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Boolean execute() {

                TableEvent tableEvent = (TableEvent) this.event;
                DatabaseInfo databaseInfo = tableEvent.getDatabaseInfo();
                Database database = Create.super.dbManagement.getDatabase(databaseInfo);
                if (database == null) {
                    database = Create.super.dbManagement.createDatabase(databaseInfo);
                    Create.super.persistenceDatabase(databaseInfo);
                }

                TableInfo tableInfo = tableEvent.getTableInfo();
                database.createTable(tableInfo);
                Create.super.persistenceTable(databaseInfo, tableInfo);
                return Boolean.TRUE;
            }
        }

        private class Callback implements FutureCallback<Boolean> {
            @Override
            public void onSuccess(@Nullable Boolean result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        }
    }

    public static class Drop extends TableListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof TableEvent.Create) {
                // 提交创建数据库任务
                TableEvent dropEvent = (TableEvent) event;
                PersistenceTask<Boolean> task = new TableListener.Drop.Task(
                        () -> System.err.println("create database task completed "), // 监听器
                        new TableListener.Drop.Callback(), // 回调函数
                        dropEvent);
                submit(task);
            }
        }

        private class Task extends PersistenceTask<Boolean> {
            Task(@Nullable Runnable taskExecuteListener,
                 @NonNull FutureCallback<Boolean> futureCallback,
                 @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Boolean execute() {

                TableEvent tableEvent = (TableEvent) this.event;
                DatabaseInfo databaseInfo = tableEvent.getDatabaseInfo();
                Database database = Drop.super.dbManagement.getDatabase(databaseInfo);

                if (database == null) {
                    database = Drop.super.dbManagement.createDatabase(databaseInfo);
                    Drop.super.persistenceDatabase(databaseInfo);
                    return Boolean.FALSE;
                }
                TableInfo tableInfo = tableEvent.getTableInfo();

                RowStoreTable table = database.getRowStoreTable(tableInfo);
                if (table == null) {
                    return Boolean.FALSE;
                }
                database.dropTable(tableInfo);
                // 删除表格文件
                return clearTable(databaseInfo, tableInfo) != null;
            }
        }

        private class Callback implements FutureCallback<Boolean> {
            @Override
            public void onSuccess(@Nullable Boolean result) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }
    }

    // TODO  暂时不支持modify操作
    public static class Modify extends TableListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof TableEvent.Create) {
                // 提交创建数据库任务
                TableEvent modifyEvent = (TableEvent) event;
                PersistenceTask<Boolean> task = new TableListener.Modify.Task(
                        () -> System.err.println("create database task completed "), // 监听器
                        new TableListener.Modify.Callback(), // 回调函数
                        modifyEvent);
                submit(task);
            }
        }

        private class Task extends PersistenceTask<Boolean> {
            Task(@Nullable Runnable taskExecuteListener,
                 @NonNull FutureCallback<Boolean> futureCallback,
                 @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Boolean execute() {
                TableEvent.Modify modifyTableEvent = (TableEvent.Modify) this.event;
                DatabaseInfo databaseInfo = modifyTableEvent.getDatabaseInfo();
                TableInfo tableInfo = modifyTableEvent.getTableInfo();
                TableInfo targetTableInfo = modifyTableEvent.getTargetTableInfo();
                Database database = Modify.super.dbManagement.getDatabase(databaseInfo);
                if (database == null) {
                    database = Modify.super.dbManagement.createDatabase(databaseInfo);
                    database.createTable(targetTableInfo);
                    // TODO  持久化数据库info信息
                    return Boolean.TRUE;
                }

                database.dropTable(tableInfo);
                // TODO 删除表格文件

                // TODO  持久化表格信息
                return Boolean.TRUE;
            }
        }

        private class Callback implements FutureCallback<Boolean> {
            @Override
            public void onSuccess(@Nullable Boolean result) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }
    }
}
