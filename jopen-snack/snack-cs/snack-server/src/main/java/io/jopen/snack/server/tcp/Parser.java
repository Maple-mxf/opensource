package io.jopen.snack.server.tcp;

import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.server.storage.DBManagement;

/**
 * 解析器顶级父类
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
abstract class Parser {

    final DBManagement dbManagement = DBManagement.DBA;

    /**
     * @param requestInfo request info
     * @return response info
     * @see SnackDBTcpServer
     */
    public abstract RpcData.S2C parse(RpcData.C2S requestInfo) throws Exception;
}
