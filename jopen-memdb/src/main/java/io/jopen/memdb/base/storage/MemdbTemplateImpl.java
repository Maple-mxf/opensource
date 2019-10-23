package io.jopen.memdb.base.storage;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.jopen.core.function.ReturnValue;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static io.jopen.memdb.base.storage.JavaModelTable.afterModifyTableCallbacks;
import static io.jopen.memdb.base.storage.JavaModelTable.preModifyTableCallbacks;

/**
 * 内存数据库
 *
 * @author maxuefeng
 * @since 2019/10/22
 */
public final
class MemdbTemplateImpl {

    //
    private Database currentDatabase;

    public ConcurrentHashMap<String, Database> showDBs() {
        return DatabaseManagement.DBA.databases;
    }

    // 客户端登陆
    private MemdbTemplateImpl() {

        // 读取持久化数据

    }

    Database getCurrentDatabase() {
        return this.currentDatabase;
    }

    // 单例
    private static MemdbTemplateImpl memTemplateInstance = null;

    static MemdbTemplateImpl getInstance() {
        synchronized (MemdbTemplateImpl.class) {
            if (memTemplateInstance == null) {
                memTemplateInstance = new MemdbTemplateImpl();
            }
            return memTemplateInstance;
        }
    }

    public static class Builder {

        private Builder() {
        }

        public static synchronized Builder startDBServer() {
            MemdbServer.MEMDB_SERVER.doStart();
            return new Builder();
        }

        /**
         * 同步加锁方式  防止数据错误
         *
         * @param dbName 数据库名称
         * @return fluent风格 build
         */
        public synchronized Builder switchDB(String dbName) {
            if (Strings.isNullOrEmpty(dbName)) {
                throw new IllegalArgumentException("database name must not null");
            }

            Database db = DatabaseManagement.DBA.databases.get(dbName);

            if (db == null) {
                db = new Database(dbName);
                DatabaseManagement.DBA.databases.put(dbName, db);
            }
            MemdbTemplateImpl.getInstance().currentDatabase = db;
            return this;
        }

        public MemdbTemplateImpl build() {
            return MemdbTemplateImpl.getInstance();
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