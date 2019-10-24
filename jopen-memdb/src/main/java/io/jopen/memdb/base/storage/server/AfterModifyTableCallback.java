package io.jopen.memdb.base.storage.server;

/**
 * 修改表格之后触发的函数
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
@FunctionalInterface
public interface AfterModifyTableCallback {

    void callback(Database database, JavaModelTable table) throws Throwable;
}
