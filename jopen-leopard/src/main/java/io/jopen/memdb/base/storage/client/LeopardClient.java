package io.jopen.memdb.base.storage.client;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Service;
import io.jopen.memdb.base.storage.server.DBManagement;
import io.jopen.memdb.base.storage.server.Database;
import io.jopen.memdb.base.storage.server.LeopardServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * application layer
 * {@link LeopardServer#startAsync()} ()} ()}
 * <p>
 * <p>
 * 数据库客户端
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
public final
class LeopardClient {

    // 当前数据库
    private Database currentDatabase;

    // private LeopardServer memDBDatabaseSystem;

    // 客户端登陆
    private LeopardClient() {
    }

    Database getCurrentDatabase() {
        return this.currentDatabase;
    }

    // 单例
    private static LeopardClient leopardClient = null;

    private static LeopardClient getInstance() {
        synchronized (LeopardClient.class) {
            if (leopardClient == null) {
                leopardClient = new LeopardClient();
            }
            return leopardClient;
        }
    }

    /**
     * 同步加锁方式  防止数据错误
     *
     * @param dbName 数据库名称
     * @return fluent风格 build
     */
    public synchronized LeopardClient switchDB(String dbName) {
        if (Strings.isNullOrEmpty(dbName)) {
            throw new IllegalArgumentException("currentDatabase name must not null");
        }

        Database db = DBManagement.DBA.getDatabase(dbName);

        if (db == null) {
            db = new Database(dbName);
            DBManagement.DBA.addDatabase(db);
        }
        this.currentDatabase = db;
        return this;
    }

    public static class Builder {
        public Builder() {
        }

        public synchronized LeopardClient.Builder startDBServer() {
            // 启动服务器
            Service.State state = LeopardServer.DB_DATABASE_SYSTEM.state();
            if (state.equals(Service.State.STOPPING) || state.equals(Service.State.FAILED)
                    || state.equals(Service.State.TERMINATED) || state.equals(Service.State.NEW)) {
                LeopardServer.DB_DATABASE_SYSTEM.startAsync();
                state = LeopardServer.DB_DATABASE_SYSTEM.state();
            }

            // 状态检测
            if (!state.equals(Service.State.RUNNING) && !state.equals(Service.State.STARTING)) {
                throw new RuntimeException("DB_DATABASE_SYSTEM not start");
            }
            return this;
        }

        public LeopardClient build() {
            return LeopardClient.getInstance();
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

    /*@SafeVarargs
    @Deprecated
    public final <T> QueryBuilder<T> input(@NonNull T... t) {
        return new QueryBuilder<>(Lists.newArrayList(t), this);
    }*/


}
