package io.jopen.memdb.base.storage.client;

import io.jopen.memdb.base.storage.server.Database;

/**
 * 上下文对象 用于传输部分数据
 *
 * @author maxuefeng
 * @since 2019/10/24
 */
final
class MemDBContext {

    public Database currentDatabase;
}
