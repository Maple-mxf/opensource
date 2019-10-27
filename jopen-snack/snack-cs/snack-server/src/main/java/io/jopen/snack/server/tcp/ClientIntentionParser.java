package io.jopen.snack.server.tcp;

import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;

import java.io.IOException;

/**
 * 客户端意图解析器
 *
 * @author maxuefeng
 * @see io.jopen.snack.common.DBObject
 * @see io.jopen.snack.common.TableInfo
 * @see io.jopen.snack.common.DatabaseInfo
 * @since 2019/10/26
 */
final
class ClientIntentionParser {

    RowOperator rowParser = new RowOperator();

    RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        SnackExceptionUtil.checkNull(requestInfo, ParamIsNullException.class, "operation type must not null!");

        // 解析操作级别
        RpcData.C2S.OperationLevel operationLevel = requestInfo.getOperationLevel();

        SnackExceptionUtil.checkNull(operationLevel, ParamIsNullException.class, "operation type must not null!");

        // 操作数据库
        if (RpcData.C2S.OperationLevel.database.equals(operationLevel)) {

            RpcData.C2S.DBOperation dbOperation = requestInfo.getDbOperation();


        }
        // 操作表格本身
        else if (RpcData.C2S.OperationLevel.table.equals(operationLevel)) {

        }
        // 操作表格数据
        else if (RpcData.C2S.OperationLevel.row.equals(operationLevel)) {
            return rowParser.parse(requestInfo);

        } else {
            throw new SnackRuntimeException("Unknow exception");
        }


        return null;
    }


}