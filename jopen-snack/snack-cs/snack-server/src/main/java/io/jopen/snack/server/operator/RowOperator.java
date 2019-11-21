package io.jopen.snack.server.operator;

import com.google.common.collect.Lists;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.*;
import io.jopen.snack.common.event.RowEvent;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.protol.RpcDataUtil;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.storage.RowStoreTable;
import io.jopen.snack.server.PersistenceContext;

import java.io.IOException;
import java.util.*;


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
                    List<Row> rows;
                    if (expressions.size() == 1) {
                        rows = targetTable.update(expressions.get(0), updateBody);
                    } else {
                        rows = targetTable.update(expressions, updateBody);
                    }

                    updateRows = rows.size();

                    if (updateRows > 0) {
                        // 持久化数据记录
                        PersistenceContext.eventSource.fireEvent(new RowEvent.Update(
                                // database info
                                KryoHelper.deserialization(requestInfo.getDbInfo().getValue().toByteArray(), DatabaseInfo.class),
                                // table info
                                KryoHelper.deserialization(requestInfo.getTableInfo().getValue().toByteArray(), TableInfo.class),
                                // rows
                                rows));
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

                if (updateRow > 0) {
                    // 持久化插入的数据记录
                    PersistenceContext.eventSource.fireEvent(new RowEvent.Insert(
                            // database info
                            KryoHelper.deserialization(requestInfo.getDbInfo().getValue().toByteArray(), DatabaseInfo.class),
                            // table info
                            KryoHelper.deserialization(requestInfo.getTableInfo().getValue().toByteArray(), TableInfo.class),
                            // rows
                            rows));
                }


                return RpcDataUtil.defaultSuccess(updateRow);
            }
            case DELETE: {
                List<Any> anyList = requestInfo.getConditionsList();

                int updateRows;
                List<Id> idList = null;

                if (anyList == null || anyList.size() == 0) {
                    idList = targetTable.delete(IntermediateExpression.buildFor(Row.class));
                } else if (anyList.size() == 1) {
                    IntermediateExpression<Row> expression = convertByteArray2Expression(anyList.get(0));
                    idList = targetTable.delete(expression);
                } else {
                    List<IntermediateExpression<Row>> expressions = convertByteArray2Expressions(anyList);
                    idList = targetTable.delete(expressions);
                }

                updateRows = idList.size();

                if (updateRows > 0) {
                    // 持久化删除数据记录
                    PersistenceContext.eventSource.fireEvent(new RowEvent.Delete(
                            // database info
                            KryoHelper.deserialization(requestInfo.getDbInfo().getValue().toByteArray(), DatabaseInfo.class),
                            // table info
                            KryoHelper.deserialization(requestInfo.getTableInfo().getValue().toByteArray(), TableInfo.class),
                            // rows
                            new HashSet<>(idList)));
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
                throw new SnackRuntimeException("unknown io.jopen.springboot.encryption.exception,cause rowOption is not match all option");
            }
        }
    }


}
