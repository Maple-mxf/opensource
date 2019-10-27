package io.jopen.snack.server.storage;

import io.jopen.snack.common.DatabaseInfo;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link SnackDBServer}
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

    public Database getDatabase(String dbName) {
        return databases.get(dbName);
    }

    public Database addDatabase(Database database) {
        this.databases.put(database.getDbName(), database);
        return database;
    }

    public ConcurrentHashMap<String, Database> getDatabases() {
        return this.databases;
    }

    public Database getDatabase(@NonNull DatabaseInfo databaseInfo) {
        return this.databases.get(databaseInfo.getName());
    }

}
