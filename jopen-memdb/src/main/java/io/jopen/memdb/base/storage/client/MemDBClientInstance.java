package io.jopen.memdb.base.storage.client;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Service;
import io.jopen.memdb.base.storage.server.DBManagement;
import io.jopen.memdb.base.storage.server.Database;
import io.jopen.memdb.base.storage.server.MemDBSystem;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * application layer
 * {@link MemDBSystem#start()} ()}
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

    // private MemDBSystem memDBDatabaseSystem;

    // 客户端登陆
    private MemDBClientInstance() {
    }

    Database getCurrentDatabase() {
        return this.currentDatabase;
    }

    // 单例
    private static MemDBClientInstance memDBClientInstance = null;

    private static MemDBClientInstance getInstance() {
        synchronized (MemDBClientInstance.class) {
            if (memDBClientInstance == null) {
                memDBClientInstance = new MemDBClientInstance();
            }
            return memDBClientInstance;
        }
    }

    public static class Builder {
        public Builder() {
        }

        public synchronized MemDBClientInstance.Builder startDBServer() {
            // 启动服务器
            Service.State state = MemDBSystem.DB_DATABASE_SYSTEM.state();
            if (state.equals(Service.State.STOPPING) || state.equals(Service.State.FAILED)
                    || state.equals(Service.State.TERMINATED)) {
                MemDBSystem.DB_DATABASE_SYSTEM.start();
            }

            // 状态检测
            if (!state.equals(Service.State.RUNNING) || !state.equals(Service.State.STARTING)) {
                throw new RuntimeException("DB_DATABASE_SYSTEM not start");
            }
            return this;
        }

        /**
         * 同步加锁方式  防止数据错误
         *
         * @param dbName 数据库名称
         * @return fluent风格 build
         */
        public synchronized MemDBClientInstance.Builder switchDB(String dbName) {
            if (Strings.isNullOrEmpty(dbName)) {
                throw new IllegalArgumentException("currentDatabase name must not null");
            }

            Database db = DBManagement.DBA.getDatabase(dbName);

            if (db == null) {
                db = new Database(dbName);
                DBManagement.DBA.addDatabase(db);
            }
            MemDBClientInstance.getInstance().currentDatabase = db;
            return this;
        }

        public MemDBClientInstance build() {
            return MemDBClientInstance.getInstance();
        }
    }

    // query  delete  update
    public <T> QueryBuilder<T> input(@Nullable IntermediateExpression<T> expression) {
        return new QueryBuilder<>(expression, this);
    }


    // save or delete or update
    public <T> QueryBuilder<T> input(@NonNull T t) {
        return new QueryBuilder<>(Lists.newArrayList(t), this);
    }

    @SafeVarargs
    public final <T> QueryBuilder<T> input(@NonNull T... t) {
        return new QueryBuilder<>(Lists.newArrayList(t), this);
    }


}
