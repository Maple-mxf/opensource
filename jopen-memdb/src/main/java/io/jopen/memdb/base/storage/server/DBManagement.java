package io.jopen.memdb.base.storage.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link MemDBSystem}
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public final
class DBManagement {

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

}
