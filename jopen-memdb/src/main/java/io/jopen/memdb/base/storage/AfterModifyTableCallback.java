package io.jopen.memdb.base.storage;

/**
 * 修改表格之后触发的函数
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
@FunctionalInterface
interface AfterModifyTableCallback {

    void callback(Database database, JavaModelTable table) throws Throwable;
}
