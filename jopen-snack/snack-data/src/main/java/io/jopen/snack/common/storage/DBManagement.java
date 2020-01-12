package io.jopen.snack.common.storage;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    public final synchronized boolean dropDatabase(@NonNull DatabaseInfo databaseInfo) {
        Database database = this.databases.remove(databaseInfo.getName());
        return database != null;
    }

    public Set<String> getDatabases(){
        return this.databases.entrySet().parallelStream().map(Map.Entry::getKey).collect(Collectors.toSet());
    }

}
