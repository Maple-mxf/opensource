package io.jopen.snack.server.tcp;

import com.google.common.collect.Lists;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.IntermediateExpression;
import io.jopen.snack.common.Row;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.protol.RpcDataUtil;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.server.storage.Database;
import io.jopen.snack.server.storage.RowStoreTable;

import java.io.IOException;
import java.util.*;


/**
 * {@link RowStoreTable}
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public final
class RowParser extends Parser {

    private final
    Executor queryExecutor = new QueryExecutor();

    private final
    Executor deleteExecutor = new DeleteExecutor();

    /**
     * @param requestInfo request info
     * @return
     * @throws IOException
     * @see HashMap
     */
    public RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        RpcData.C2S.RowOperation rowOption = requestInfo.getRowOption();

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

                RowStoreTable targetTable = getTargetTable(requestInfo);

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

                // 进行保存
                RowStoreTable rowStoreTable = getTargetTable(requestInfo);

                // 进行保存  可能会抛出异常
                int updateRow = rowStoreTable.saveBatch(rows);

                return RpcDataUtil.defaultSuccess(updateRow);
            }
            case DELETE: {
                List<Any> anyList = requestInfo.getConditionsList();
                int updateRows;
                if (anyList == null || anyList.size() == 0) {
                    updateRows = queryExecutor.delete(IntermediateExpression.buildFor(Row.class));
                } else if (anyList.size() == 1) {
                    IntermediateExpression<Row> expression = queryExecutor.convertByteArray2Expression(anyList.get(0));
                    updateRows = deleteExecutor.delete(expression);
                } else {
                    List<IntermediateExpression<Row>> expressions = deleteExecutor.convertByteArray2Expressions(anyList);
                    updateRows = deleteExecutor.delete(expressions);
                }
                return RpcDataUtil.defaultSuccess(updateRows);
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

                Any any = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(collection))).build();
                return RpcDataUtil.defaultSuccess(any);
            }
            default: {
                throw new SnackRuntimeException("unknown exception,cause rowOption is not match all option");
            }
        }
    }

    private RowStoreTable getTargetTable(RpcData.C2S requestInfo) throws IOException {
        byte[] dbBytes = requestInfo.getDbInfo().toByteArray();
        Database database = super.dbManagement.getDatabase(KryoHelper.deserialization(dbBytes, DatabaseInfo.class));
        return database.getRowStoreTable(KryoHelper.deserialization(dbBytes, TableInfo.class));
    }

}
