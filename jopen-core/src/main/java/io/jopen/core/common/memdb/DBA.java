package io.jopen.core.common.memdb;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maxuefeng
 * @since 2019/10/22
 */
@Deprecated
final
class DBA {

    private ConcurrentHashMap<String, Database> databases = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, Database> showDBs() {
        return this.databases;
    }

    // 客户端登陆
    private DBA() {
    }

    public static final DBA DBA = new DBA();

    public Database use(String dBName) {
        return databases.getOrDefault(dBName, new Database(dBName));
    }
}
