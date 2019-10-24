package io.jopen.memdb.base.storage.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link MemDBDatabaseSystem}
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public final
class DatabaseManagement {

    private DatabaseManagement() {
    }

    public final static DatabaseManagement DBA = new DatabaseManagement();

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
