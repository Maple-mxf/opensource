package io.jopen.memdb.base.storage.deprecated;

import io.jopen.memdb.base.storage.server.Database;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
@FunctionalInterface
@Deprecated
public interface PreModifyTableCallback {

    // 先决条件
    ReturnValue prerequisites(Database database, Object object) throws Throwable;
}
