package io.jopen.memdb.base.storage.client;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.Service;
import io.jopen.memdb.base.storage.server.Database;
import io.jopen.memdb.base.storage.server.DatabaseManagement;
import io.jopen.memdb.base.storage.server.MemDBDatabaseSystem;

import java.util.Collection;

/**
 * application layer
 * {@link MemDBDatabaseSystem#start()} ()}
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
            MemDBDatabaseSystem.DB_DATABASE_SYSTEM.start();
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

            Database db = DatabaseManagement.DBA.getDatabase(dbName);

            if (db == null) {
                db = new Database(dbName);
                DatabaseManagement.DBA.addDatabase(db);
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
        return false;
    }
}
