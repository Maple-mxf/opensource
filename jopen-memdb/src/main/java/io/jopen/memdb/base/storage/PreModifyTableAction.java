package io.jopen.memdb.base.storage;

import io.jopen.core.function.ReturnValue;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
@FunctionalInterface
public interface PreModifyTableAction {
    
    // 先决条件
    ReturnValue prerequisites(Database database, Object object) throws Throwable;
}
