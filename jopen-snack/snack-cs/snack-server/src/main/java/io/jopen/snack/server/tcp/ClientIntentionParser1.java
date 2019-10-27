package io.jopen.snack.server.tcp;

import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;

/**
 * 客户端意图解析器
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
final
class ClientIntentionParser1 {

    private final
    Executor queryExecutor = new QueryExecutor();
    private final
    Executor deleteExecutor = new DeleteExecutor();

    RpcData.S2C parse(RpcData.C2S requestInfo) {

        SnackExceptionUtil.checkNull(requestInfo, ParamIsNullException.class, "operation type must not null!");

        // 解析操作级别
        RpcData.C2S.OperationLevel operationLevel = requestInfo.getOperationLevel();

        SnackExceptionUtil.checkNull(operationLevel, ParamIsNullException.class, "operation type must not null!");

        if (RpcData.C2S.OperationLevel.database.equals(operationLevel)) {

        } else if (RpcData.C2S.OperationLevel.table.equals(operationLevel)) {

        } else if (RpcData.C2S.OperationLevel.row.equals(operationLevel)) {

        } else {
            throw new SnackRuntimeException("Unknow exception");
        }


        return null;
    }

}