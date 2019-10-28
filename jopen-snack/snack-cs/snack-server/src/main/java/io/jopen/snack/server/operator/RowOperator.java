package io.jopen.snack.server.operator;

import com.google.common.collect.Lists;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.protol.RpcDataUtil;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.storage.RowStoreTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * {@link RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public final
class RowOperator extends Operator {

    /**
     * @param requestInfo request info
     * @return
     * @throws IOException
     * @see HashMap
     */
    public RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        RpcData.C2S.RowOperation rowOption = requestInfo.getRowOption();
        RowStoreTable targetTable = getTargetTable(requestInfo);

        switch (rowOption) {
            case UPDATE: {
                List<Any> conditionsList = requestInfo.getConditionsList();
                List<IntermediateExpression<Row>> expressions = new ArrayList<>();
                for (Any any : conditionsList) {
                    expressions.add(KryoHelper.deserialization(any.getTypeUrlBytes().toByteArray(), IntermediateExpression.class));
                }

                if (expressions.size() == 0) {
                    return RpcDataUtil.defaultSuccess();
                }

                if (requestInfo.hasUpdateBody()) {
                    HashMap<String, Object> updateBody = KryoHelper.deserialization(requestInfo.getUpdateBody().getValue().toByteArray(), HashMap.class);
                    int updateRows;
                    if (expressions.size() == 1) {
                        updateRows = targetTable.update(expressions.get(0), updateBody).size();
                    } else {
                        updateRows = targetTable.update(expressions, updateBody).size();
                    }
                    return RpcDataUtil.defaultSuccess(updateRows);
                } else {
                    return RpcDataUtil.defaultSuccess();
                }
            }
            case INSERT: {

                List<Row> rows = Lists.newArrayList();
                List<Any> rowAnyList = requestInfo.getRowsList();
                for (Any any : rowAnyList) {
                    rows.add(KryoHelper.deserialization(any.getValue().toByteArray(), Row.class));
                }

                // 进行保存  可能会抛出异常
                int updateRow = targetTable.saveBatch(rows);

                return RpcDataUtil.defaultSuccess(updateRow);
            }
            case DELETE: {
                List<Any> anyList = requestInfo.getConditionsList();

                int updateRows;
                if (anyList == null || anyList.size() == 0) {
                    updateRows = targetTable.delete(IntermediateExpression.buildFor(Row.class)).size();
                } else if (anyList.size() == 1) {
                    IntermediateExpression<Row> expression = convertByteArray2Expression(anyList.get(0));
                    updateRows = targetTable.delete(expression).size();
                } else {
                    List<IntermediateExpression<Row>> expressions = convertByteArray2Expressions(anyList);
                    updateRows = targetTable.delete(expressions).size();
                }
                return RpcDataUtil.defaultSuccess(updateRows);
            }
            case SELECT: {

                List<Any> anyList = requestInfo.getConditionsList();
                Collection<Row> collection;

                if (anyList == null || anyList.size() == 0) {
                    collection = targetTable.query(IntermediateExpression.buildFor(Row.class));
                } else if (anyList.size() == 1) {
                    IntermediateExpression<Row> expression = convertByteArray2Expression(anyList.get(0));
                    collection = targetTable.query(expression);
                } else {
                    List<IntermediateExpression<Row>> expressions = convertByteArray2Expressions(anyList);
                    collection = targetTable.query(expressions);
                }

                Any any = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(collection))).build();
                return RpcDataUtil.defaultSuccess(any);
            }
            default: {
                throw new SnackRuntimeException("unknown exception,cause rowOption is not match all option");
            }
        }
    }


}
