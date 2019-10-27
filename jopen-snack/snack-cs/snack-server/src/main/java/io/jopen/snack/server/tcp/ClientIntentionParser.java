package io.jopen.snack.server.tcp;

import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.server.operator.DatabaseOperator;
import io.jopen.snack.server.operator.RowOperator;
import io.jopen.snack.server.operator.TableOperator;

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

    private DatabaseOperator databaseOperator = new DatabaseOperator();
    private TableOperator tableOperator = new TableOperator();
    private RowOperator rowOperator = new RowOperator();

    RpcData.S2C parse(RpcData.C2S requestInfo) throws Exception {

        SnackExceptionUtil.checkNull(requestInfo, ParamIsNullException.class, "operation type must not null!");

        // 解析操作级别
        RpcData.C2S.OperationLevel operationLevel = requestInfo.getOperationLevel();

        // 解析为空
        SnackExceptionUtil.checkNull(operationLevel, ParamIsNullException.class, "operation type must not null!");

        // 操作数据库
        if (RpcData.C2S.OperationLevel.database.equals(operationLevel)) {
            return databaseOperator.parse(requestInfo);
        }

        // 操作表格本身
        else if (RpcData.C2S.OperationLevel.table.equals(operationLevel)) {
            return tableOperator.parse(requestInfo);
        }

        // 操作表格数据
        else if (RpcData.C2S.OperationLevel.row.equals(operationLevel)) {
            return rowOperator.parse(requestInfo);
        } else {
            throw new SnackRuntimeException("Unknown xception");
        }
    }


}