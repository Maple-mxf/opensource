package io.jopen.snack.server.storage;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link SnackDBServer}
 * {@link Database}
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public final
class DBManagement implements Serializable {

    private DBManagement() {
    }

    public final static DBManagement DBA = new DBManagement();

    private ConcurrentHashMap<String, Database> databases = new ConcurrentHashMap<>();

    @Deprecated
    public Database getDatabase(String dbName) {
        return databases.get(dbName);
    }

    @Deprecated
    public Database addDatabase(Database database) {
        this.databases.put(database.getDbName(), database);
        return database;
    }

    @Deprecated
    public ConcurrentHashMap<String, Database> getDatabases() {
        return this.databases;
    }

    public Database getDatabase(@NonNull DatabaseInfo databaseInfo) {
        return this.databases.get(databaseInfo.getName());
    }

    public Database createDatabase(DatabaseInfo databaseInfo) {
        Database database = new Database(databaseInfo.getName());
        synchronized (this) {
            // 检测重读性
            SnackExceptionUtil.check(input -> {
                assert input != null;
                return DBManagement.this.databases.get(input.getName()) == null;
            }, databaseInfo, SnackRuntimeException.class, String.format("database exist,database info [ %s ]", databaseInfo));

            // 加入集合
            this.databases.put(database.getDbName(), database);
        }
        return database;
    }

    /**
     * 如果不存在指定database则需要主动创建
     *
     * @param databaseInfo
     * @return
     */
    @NonNull
    public final Database securityGetDatabase(@NonNull DatabaseInfo databaseInfo) {
        try {
            return this.createDatabase(databaseInfo);
        } catch (SnackRuntimeException ex) {
            ex.printStackTrace();
            return this.getDatabase(databaseInfo);
        }
    }

}
