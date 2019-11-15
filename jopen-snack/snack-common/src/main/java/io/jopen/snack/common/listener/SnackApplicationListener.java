package io.jopen.snack.common.listener;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.google.common.util.concurrent.*;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.storage.DBManagement;
import io.jopen.snack.common.storage.Database;
import io.jopen.snack.common.storage.RowStoreTable;
import io.jopen.snack.common.task.PersistenceTask;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static io.jopen.snack.common.constant.FileConst.*;

/**
 * <p>{@link io.jopen.snack.common.event.SnackApplicationEvent}</p>
 *
 * @author maxuefeng
 * @see com.google.common.util.concurrent.ThreadFactoryBuilder
 * @see java.lang.Thread.UncaughtExceptionHandler
 * @see org.apache.commons.lang3.ThreadUtils
 * @see org.apache.commons.lang3.concurrent.BasicThreadFactory
 * <p>{@link ExecutionException} 任务执行异常 可能并不会在外层接收到此异常</p>
 * <p>{@link InterruptedException} 线程中断异常，此异常认为因素大一点</p>
 * <p>{@link TimeoutException} 任务执行超时异常，跟所设定的执行时间期限有关系</p>
 * @see ExecutorService
 * {@code}
 * @since 2019/10/27
 */
public abstract class SnackApplicationListener<V> {

    private final BlockingQueue<Runnable> threadBlockingQueue = new LinkedBlockingQueue<>();

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("snack-listener-%d")
            .setUncaughtExceptionHandler((thread, e) -> System.err.println(String.format("Thread %s create failure ,cause [ %s ]", thread.getName(), e.getMessage())))
            .setPriority(Thread.NORM_PRIORITY)
            .build();

    /**
     * @see ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue, ThreadFactory, RejectedExecutionHandler)
     * @see Callable
     * @see Executors
     * @see BasicThreadFactory
     * @see ThreadFactoryBuilder
     * @see RejectedExecutionException
     */
    private final ExecutorService javaService = new ThreadPoolExecutor(
            // 线程核心数量
            50,
            // 最大数量
            100,
            // 执行完任务存活的最大时间
            20000L,
            // 时间单位
            TimeUnit.MICROSECONDS,
            // 线程队列
            threadBlockingQueue,
            // 线程构建工厂
            threadFactory,
            // 线程池任务拒绝策略  有两种情况会出现这种情况 （1 线程池的数量不够了  2 阻塞队列的容量不够了）
            new ThreadPoolExecutor.AbortPolicy()
    );

    // 执行任务的监听器
    private final ListeningExecutorService guavaDecoratorService = MoreExecutors.listeningDecorator(javaService);

    private final BlockingQueue<PersistenceTask<V>> taskQueue = new LinkedBlockingQueue<>();

    final DBManagement dbManagement = DBManagement.DBA;

    final void submit(@NonNull PersistenceTask<V> task) {
        try {
            this.taskQueue.put(task);
        } catch (InterruptedException ignored) {
        }
    }

    // 把事件对象作为参数
    public abstract void apply(@NonNull SnackApplicationEvent event);

    /**
     * 启动任务执行
     * <p>{@link RejectedExecutionException}  任务拒绝策略</p>
     */
    public void start() {
        new Thread(() -> {
            while (true) {
                PersistenceTask<V> persistenceTask = null;
                try {
                    persistenceTask = SnackApplicationListener.this.taskQueue.take();

                    // 提交任务会抛出异常  线程池的拒绝策略
                    ListenableFuture<V> future = this.guavaDecoratorService.submit(persistenceTask);
                    future.addListener(persistenceTask.getTaskExecuteListener(), this.guavaDecoratorService);
                    Futures.addCallback(future, persistenceTask.getFutureCallback(), this.guavaDecoratorService);

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        // 继续重试
                        if (persistenceTask != null) {
                            this.taskQueue.add(persistenceTask);
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    protected static File topDir = new File("snackDB");

    final void persistenceOutside() {
        boolean exists = topDir.exists();
        topDir.setReadable(true);
        if (!topDir.isDirectory()) {
            topDir.delete();
        }

        if (!exists || !topDir.isDirectory()) {
            topDir.mkdirs();
        }
    }

    final Database persistenceDatabase(DatabaseInfo databaseInfo) {
        Database database = this.dbManagement.createDatabase(databaseInfo);
        String dbPath = dbPath(databaseInfo);
        File dbDir = new File(dbPath);


        // 持久化数据库信息 {DatabaseInfo}
        try {
            dbDir.mkdir();
            File dbInfoFile = new File(dbDir + "/" + databaseInfo.getName() + "." + dbInfoFileSuffix);
            Files.write(KryoHelper.serialization(databaseInfo), dbInfoFile);
            return database;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    final RowStoreTable persistenceTable(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        Database database = this.dbManagement.getDatabase(databaseInfo);

        if (database == null) {
            database = persistenceDatabase(databaseInfo);
        }
        if (database == null) {
            return null;
        }

        RowStoreTable rowStoreTable = database.getRowStoreTable(tableInfo);
        if (rowStoreTable != null) {
            return rowStoreTable;
        }

        rowStoreTable = database.createTable(tableInfo);
        String dbPath = dbPath(databaseInfo);
        File destTableDataFile = new File(dbPath + "/" + tableInfo.getName() + "." + tableDataFileSuffix);
        File destTableInfoFile = new File(dbPath + "/" + tableInfo.getName() + "." + tableInfoFileSuffix);

        try {
            // 创建数据文件
            Files.write(KryoHelper.serialization(rowStoreTable), destTableDataFile);
        } catch (IOException ignored) {
            return null;
        }

        try {
            // 创建表格信息文件
            Files.write(KryoHelper.serialization(tableInfo), destTableInfoFile);
        } catch (IOException ignored) {
            return null;
        }
        return rowStoreTable;
    }


    final RowStoreTable clearTable(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        Database database = this.dbManagement.getDatabase(databaseInfo);

        if (database == null) {
            database = persistenceDatabase(databaseInfo);
        }
        if (database == null) {
            return null;
        }

        RowStoreTable rowStoreTable = database.getRowStoreTable(tableInfo);
        if (rowStoreTable == null) {
            return null;
        }

        String dbPath = dbPath(databaseInfo);
        // 清除数据文件
        // 清除info文件
        File tableInfoFile = new File(dbPath + "/" + tableInfo.getName() + "." + tableInfoFileSuffix);
        File tableDataFile = new File(dbPath + "/" + tableInfo.getName() + "." + tableDataFileSuffix);

        try {
            tableInfoFile.setWritable(true);
            tableDataFile.setWritable(true);
            tableInfoFile.delete();
            tableDataFile.delete();
            return rowStoreTable;
        } catch (Exception ignored) {
            return null;
        }

    }

    private String dbPath(DatabaseInfo databaseInfo) {
        String path = topDir.getAbsolutePath();
        return Joiner.on("/").join(new String[]{path, databaseInfo.getName()});
    }

    @Deprecated
    final Predicate<DatabaseInfo> createDB = databaseInfo -> {
        Database result = SnackApplicationListener.this.dbManagement.createDatabase(databaseInfo);
        if (result == null) {
            return false;
        }
        result = SnackApplicationListener.this.persistenceDatabase(databaseInfo);

        if (result == null) {
            SnackApplicationListener.this.dbManagement.dropDatabase(databaseInfo);
            return false;
        }
        return true;
    };

    final BiPredicate<DatabaseInfo, TableInfo> createTable = (databaseInfo, tableInfo) -> {
        Database database = SnackApplicationListener.this.dbManagement.getDatabase(databaseInfo);
        if (database == null) {
            // 创建数据库
            dbManagement.createDatabase(databaseInfo);
            database = persistenceDatabase(databaseInfo);

            if (database == null) {
                dbManagement.dropDatabase(databaseInfo);
                return false;
            }
        }

        RowStoreTable table = database.getRowStoreTable(tableInfo);

        if (table != null) {
            return false;
        }

        database.createTable(tableInfo);
        table = persistenceTable(databaseInfo, tableInfo);

        return table != null;
    };
}