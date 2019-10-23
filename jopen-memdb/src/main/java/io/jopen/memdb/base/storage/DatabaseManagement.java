package io.jopen.memdb.base.storage;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
final
class DatabaseManagement {

    private DatabaseManagement() {
    }

    static DatabaseManagement DBA = new DatabaseManagement();

    ConcurrentHashMap<String, Database> databases = new ConcurrentHashMap<>();


}
