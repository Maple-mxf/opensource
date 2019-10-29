package io.jopen.snack.common.listener;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.event.DatabaseEvent;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.task.PersistenceTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.File;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class DatabaseListener extends SnackApplicationListener<Boolean> {

    public static class Create extends DatabaseListener {

        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof DatabaseEvent.Create) {
                // 提交创建数据库任务
                DatabaseEvent createEvent = (DatabaseEvent) event;
                PersistenceTask<Boolean> task = new Task(() -> System.err.println("create database task completed "), new Callback(), createEvent);
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
                try {
                    DatabaseEvent createEvent = (DatabaseEvent) this.event;
                    DatabaseInfo databaseInfo = createEvent.getDatabaseInfo();
                    Create.this.dbManagement.createDatabase(databaseInfo);
                    // 进行持久化操作
                    // 检测外部
                    Create.super.persistenceOutside();
                    //
                    String path = topDir.getAbsolutePath();
                    String dbPath = Joiner.on("/").join(new String[]{path, databaseInfo.getName()});
                    File dbDir = new File(dbPath);
                    dbDir.mkdir();

                    // 持久化数据库信息 {DatabaseInfo}
                    File dbInfoFile = new File(dbDir + "/dbinfo.sdb");
                    Files.write(KryoHelper.serialization(databaseInfo), dbInfoFile);

                    return Boolean.TRUE;
                } catch (Exception e) {
                    e.printStackTrace();
                    return Boolean.FALSE;
                }
            }
        }

        // 回调
        private class Callback implements FutureCallback<Boolean> {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                System.err.println(String.format("database create success result [ %s ] ", result));
            }

            @Override
            public void onFailure(Throwable t) {
                t.getCause().printStackTrace();
            }
        }
    }


    public static class Drop extends SnackApplicationListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof DatabaseEvent.Drop) {
                DatabaseEvent.Drop dropEvent = (DatabaseEvent.Drop) event;
                PersistenceTask<Boolean> task = new Create.Task(() -> System.err.println("create database task completed "), new Create.Callback(), dropEvent);
                submit(task);
            }
        }
    }
}
