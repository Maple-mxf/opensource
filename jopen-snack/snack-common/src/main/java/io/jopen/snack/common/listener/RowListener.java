package io.jopen.snack.common.listener;

import com.google.common.base.Joiner;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.*;
import io.jopen.snack.common.event.RowEvent;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.storage.Database;
import io.jopen.snack.common.storage.RowStoreTable;
import io.jopen.snack.common.task.PersistenceTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>{@link RowStoreTable}</p>
 *
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract
class RowListener extends SnackApplicationListener {


    private File getTempTableFileTypesOfInsert(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        String filePath = Joiner.on("/").join(new String[]{topDir.getAbsolutePath(), databaseInfo.getName(), tableInfo.getName()}) + "-insert-temp.rdb";
        return new File(filePath);
    }

    private File getTempTableFileTypesOfDelete(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        String filePath = Joiner.on("/").join(new String[]{topDir.getAbsolutePath(), databaseInfo.getName(), tableInfo.getName()}) + "-delete-temp.rdb";
        return new File(filePath);
    }

    private File getTempTableFileTypesOfUpdate(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        String filePath = Joiner.on("/").join(new String[]{topDir.getAbsolutePath(), databaseInfo.getName(), tableInfo.getName()}) + "-update-temp.rdb";
        return new File(filePath);
    }

    public class Insert extends RowListener {
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
            public Integer execute() throws IOException {
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
                Collection<Row> rows = insertEvent.getRows();

                if (rows.size() == 0) {
                    return 0;
                }
                // 写入文件
                RowListener.this.atomicChangeTempFile(TypesOf.insert, databaseInfo, tableInfo, rows, null, null);

                return rows.size();
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

    public class Delete extends RowListener {
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
            public Integer execute() throws IOException {
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

                // 进行持久化操作
                RowListener.this.atomicChangeTempFile(TypesOf.insert, databaseInfo, tableInfo, null, deleteEvent.getDeleteIds(), null);
                return deleteEvent.getDeleteIds().size();
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

    public class Update extends RowListener {

        @Override
        public void apply(@NonNull SnackApplicationEvent event) {

            if (event instanceof RowEvent.Delete) {

            }
        }

        private class Task extends PersistenceTask<Integer> {
            protected Task(@Nullable Runnable taskExecuteListener, @NonNull FutureCallback<Integer> futureCallback, @NonNull SnackApplicationEvent event) {
                super(taskExecuteListener, futureCallback, event);
            }

            @Override
            public Integer execute() throws IOException {
                RowEvent.Update updateEvent = (RowEvent.Update) event;
                DatabaseInfo databaseInfo = updateEvent.getDatabaseInfo();
                TableInfo tableInfo = updateEvent.getTableInfo();


                Database database = Update.super.dbManagement.getDatabase(databaseInfo);
                if (database == null) {
                    Update.super.dbManagement.createDatabase(databaseInfo);
                    database = Update.super.persistenceDatabase(databaseInfo);
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

                // 进行持久化操作
                RowListener.this.atomicChangeTempFile(TypesOf.insert, databaseInfo, tableInfo, null, null, updateEvent.getRows());

                return null;
            }
        }
    }


    private enum TypesOf {
        insert,
        update,
        delete
    }


    /**
     * @param typesOf
     * @param databaseInfo
     * @param tableInfo
     * @param insertRows
     * @throws IOException
     * @see RowStoreTable
     */
    private void atomicChangeTempFile(@NonNull TypesOf typesOf,
                                      @NonNull DatabaseInfo databaseInfo,
                                      @NonNull TableInfo tableInfo,
                                      Collection<Row> insertRows,
                                      Collection<Id> deleteIds,
                                      Collection<Row> updateRows) throws IOException {
        synchronized (this) {
            switch (typesOf) {
                case delete: {

                    File file = getTempTableFileTypesOfDelete(databaseInfo, tableInfo);
                    byte[] bytes = Files.asByteSource(file).read();
                    Collection<Id> rows = KryoHelper.deserialization(bytes, ArrayList.class);
                    if (rows == null) {
                        rows = new HashSet<>();
                    }
                    Set<Id> emptySet = new HashSet<>();
                    emptySet.addAll(rows);
                    emptySet.addAll(deleteIds);

                    byte[] finalBytes = KryoHelper.serialization(emptySet);

                    // 保存
                    Files.asByteSink(file, FileWriteMode.APPEND).write(finalBytes);
                    break;
                }
                case insert: {
                    // 读取文件
                    File file = getTempTableFileTypesOfInsert(databaseInfo, tableInfo);
                    byte[] bytes = Files.asByteSource(file).read();
                    Collection<Row> rows = KryoHelper.deserialization(bytes, ArrayList.class);

                    if (rows == null) {
                        rows = new ArrayList<>();
                    }
                    rows.addAll(insertRows);

                    byte[] finalBytes = KryoHelper.serialization(rows);

                    // 保存
                    Files.asByteSink(file, FileWriteMode.APPEND).write(finalBytes);
                    break;
                }
                case update: {
                    // 读取文件
                    File file = getTempTableFileTypesOfUpdate(databaseInfo, tableInfo);
                    byte[] bytes = Files.asByteSource(file).read();
                    Collection<Row> rows = KryoHelper.deserialization(bytes, ArrayList.class);

                    if (rows == null) {
                        rows = new ArrayList<>();
                    }

                    rows.addAll(updateRows);

                    byte[] finalBytes = KryoHelper.serialization(rows);

                    // 保存
                    Files.asByteSink(file, FileWriteMode.APPEND).write(finalBytes);
                    break;
                }
            }
        }
    }
}
