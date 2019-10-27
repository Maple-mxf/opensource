package io.jopen.snack.server.tcp;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.serialize.KryoHelper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.jopen.snack.common.protol.Message.success;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class RowParser {

    private final
    Executor queryExecutor = new QueryExecutor();

    private final
    Executor deleteExecutor = new DeleteExecutor();

    RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        RpcData.C2S.RowOperation rowOption = requestInfo.getRowOption();

        //
        if (rowOption.equals(RpcData.C2S.RowOperation.SELECT)) {

        }

        switch (rowOption) {
            case UPDATE: {
                break;
            }
            case INSERT: {

                List<Any> rowAnyList = requestInfo.getRowsList();
                for (Any any : rowAnyList) {

                }


                break;
            }
            case DELETE: {
                List<Any> anyList = requestInfo.getConditionsList();
                int updateRows = 0;
                if (anyList == null || anyList.size() == 0) {
                    updateRows = queryExecutor.delete(IntermediateExpression.buildFor(Row.class));
                } else if (anyList.size() == 1) {
                    IntermediateExpression<Row> expression = queryExecutor.convertByteArray2Expression(anyList.get(0));
                    updateRows = deleteExecutor.delete(expression);
                } else {
                    List<IntermediateExpression<Row>> expressions = deleteExecutor.convertByteArray2Expressions(anyList);
                    updateRows = deleteExecutor.delete(expressions);
                }
                return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).setUpdateRow(updateRows).build();
            }
            case SELECT: {

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

                Any.Builder value = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(collection)));
                return RpcData.S2C.newBuilder().setCode(success.getCode()).setErrMsg(success.getMsg()).setCollectionRes(0, value).setUpdateRow(0).build();

                break;
            }
            default: {
                throw new SnackRuntimeException("unknown exception,cause rowOption is not match all option");
            }
        }
    }

}
