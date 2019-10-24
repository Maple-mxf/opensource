package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.Service;
import io.jopen.core.function.ReturnValue;

import java.util.Collection;

import static io.jopen.memdb.base.storage.JavaModelTable.afterModifyTableCallbacks;
import static io.jopen.memdb.base.storage.JavaModelTable.preModifyTableCallbacks;

/**
 * {@link MemDBDatabaseSystem#doStart()}
 * {@link MemDBDatabaseSystem#doStop()} ()}
 * <p>
 * <p>
 * 数据库客户端
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
public class MemDBClientInstance {

    // 当前数据库
    private Database currentDatabase;

    // private MemDBDatabaseSystem memDBDatabaseSystem;

    // 客户端登陆
    private MemDBClientInstance() {

        // 启动服务器
        Service.State state = MemDBDatabaseSystem.DB_DATABASE_SYSTEM.state();
        if (state.equals(Service.State.STOPPING) || state.equals(Service.State.FAILED)
                || state.equals(Service.State.TERMINATED)) {
            MemDBDatabaseSystem.DB_DATABASE_SYSTEM.doStart();
        }

        // 状态检测
        if (!state.equals(Service.State.RUNNING) || !state.equals(Service.State.STARTING)) {
            throw new RuntimeException("DB_DATABASE_SYSTEM not start");
        }
    }

    Database getCurrentDatabase() {
        return this.currentDatabase;
    }

    // 单例
    private static MemDBClientInstance memDBClientInstance = null;

    static MemDBClientInstance getInstance() {
        synchronized (MemdbTemplateImpl.class) {
            if (memDBClientInstance == null) {
                memDBClientInstance = new MemDBClientInstance();
            }
            return memDBClientInstance;
        }
    }

    public static class Builder {
        private Builder() {
        }

        /**
         * 同步加锁方式  防止数据错误
         *
         * @param dbName 数据库名称
         * @return fluent风格 build
         */
        public synchronized MemDBClientInstance.Builder switchDB(String dbName) {
            if (Strings.isNullOrEmpty(dbName)) {
                throw new IllegalArgumentException("database name must not null");
            }

            Database db = DatabaseManagement.DBA.databases.get(dbName);

            if (db == null) {
                db = new Database(dbName);
                DatabaseManagement.DBA.databases.put(dbName, db);
            }
            MemDBClientInstance.getInstance().currentDatabase = db;
            return this;
        }

        public MemDBClientInstance build() {
            return MemDBClientInstance.getInstance();
        }
    }

    public MemdbExecutor select() {
        return new MemdbExecutor(OperationType.SELECT);
    }

    public MemdbExecutor delete() {
        return new MemdbExecutor(OperationType.DELETE);
    }

    public MemdbExecutor update() {
        return new MemdbExecutor(OperationType.UPDATE);
    }

    public MemdbExecutor insert() {
        return new MemdbExecutor(OperationType.UPDATE);
    }


    public <T> Boolean saveBatch(Collection<T> entities) throws Throwable {
        for (T entity : entities) {
            save(entity);
        }
        return true;
    }


    public <T> Boolean save(T t) throws Throwable {
        Preconditions.checkNotNull(t);

        JavaModelTable<T> targetTable = null;
        // 执行J修改表格之前的预操作
        for (PreModifyTableCallback preAction : preModifyTableCallbacks) {
            ReturnValue returnValue = preAction.prerequisites(getInstance().currentDatabase, t);

            if (returnValue.containsKey(t.getClass().getName())) {
                targetTable = (JavaModelTable<T>) returnValue.get(t.getClass().getName());
            }
        }

        // 程序错误
        if (targetTable == null) {
            throw new RuntimeException("unknow error happen");
        }

        // 保存数据
        Boolean Res = targetTable.add(t);
        for (AfterModifyTableCallback afterModifyTableCallback : afterModifyTableCallbacks) {
            afterModifyTableCallback.callback(this.currentDatabase, targetTable);
        }
        return Res;
    }

    @Deprecated
    private <T> void delete(T t) throws Throwable {

        Preconditions.checkNotNull(t);
        JavaModelTable<T> targetTable = null;
        // 执行J修改表格之前的预操作
        for (PreModifyTableCallback preAction : preModifyTableCallbacks) {
            ReturnValue returnValue = preAction.prerequisites(this.currentDatabase, t);

            if (returnValue.containsKey(t.getClass().getName())) {
                targetTable = (JavaModelTable<T>) returnValue.get(t.getClass().getName());
            }
        }
        targetTable.delete(t);
    }

}
