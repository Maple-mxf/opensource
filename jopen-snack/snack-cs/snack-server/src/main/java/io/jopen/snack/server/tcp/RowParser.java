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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.jopen.snack.common.protol.Message.success;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public final
class RowParser extends Parser {

    private final
    Executor queryExecutor = new QueryExecutor();

    private final
    Executor deleteExecutor = new DeleteExecutor();

    public RpcData.S2C parse(RpcData.C2S requestInfo) throws IOException {

        RpcData.C2S.RowOperation rowOption = requestInfo.getRowOption();

        switch (rowOption) {
            case UPDATE: {
                break;
            }
            case INSERT: {

                List<Row> rows = Lists.newArrayList();
                List<Any> rowAnyList = requestInfo.getRowsList();
                for (Any any : rowAnyList) {
                    rows.add(KryoHelper.deserialization(any.getValue().toByteArray(), Row.class));
                }
                // 进行保存
                // dbManagement
                byte[] dbBytes = requestInfo.getDbInfo().toByteArray();
                Database database = super.dbManagement.getDatabase(KryoHelper.deserialization(dbBytes, DatabaseInfo.class));
                RowStoreTable rowStoreTable = database.getRowStoreTable(KryoHelper.deserialization(dbBytes, TableInfo.class));

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
