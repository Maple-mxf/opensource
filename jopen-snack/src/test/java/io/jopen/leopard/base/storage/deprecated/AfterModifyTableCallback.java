package io.jopen.leopard.base.storage.deprecated;

import io.jopen.leopard.base.storage.server.Database;

/**
 * 修改表格之后触发的函数
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
@FunctionalInterface
@Deprecated
public interface AfterModifyTableCallback {

    void callback(Database database, JavaModelTable table) throws Throwable;
}
