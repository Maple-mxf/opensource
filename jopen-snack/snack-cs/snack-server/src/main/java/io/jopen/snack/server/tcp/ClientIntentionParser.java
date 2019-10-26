package io.jopen.snack.server.tcp;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.serialize.KryoHelper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.jopen.snack.common.protol.Message.failure;
import static io.jopen.snack.common.protol.Message.success;
import static io.jopen.snack.common.protol.RpcData.C2S.OperationType.*;

/**
 * 客户端意图解析器
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
final
class ClientIntentionParser {

    Executor queryExecutor = new QueryExecutor();

    RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        // 获取操作符
        RpcData.C2S.OperationType operationType;

        // 参数检验
        if (requestInfo == null || (operationType = requestInfo.getOperationType()) == null) {
            return RpcData.S2C.newBuilder().setCode(failure.getCode()).setErrMsg(failure.getMsg()).build();
        }

        // 查询表格 -> 执行查询
        if (operationType.equals(QUERY)) {
            List<Any> anyList = requestInfo.getConditionsList();
            Collection<Map<String, Object>> collection;
            if (anyList == null || anyList.size() == 0) {
                collection = queryExecutor.query(IntermediateExpression.buildFor(Row.class));
            } else if (anyList.size() == 1) {
                IntermediateExpression<Row> expression = queryExecutor.convertByteArray2Expression(anyList.get(0));
                collection = queryExecutor.query(expression);
            } else {
                List<IntermediateExpression<Row>> expressions = queryExecutor.convertByteArray2Expressions(anyList);
                collection = queryExecutor.query(expressions);
            }

            //
            Any.Builder value = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(collection)));
            return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).setCollectionRes(0, value).setUpdateRow(0).build();
        }
        // 删除数据
        else if (operationType.equals(DELETE)) {
        }
        // 更新数据
        else if (operationType.equals(UPDATE)) {

        }
        // 保存数据
        else if (operationType.equals(INSERT)) {

        }
    }
}
