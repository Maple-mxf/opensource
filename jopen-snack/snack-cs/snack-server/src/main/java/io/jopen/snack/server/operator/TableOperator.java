package io.jopen.snack.server.operator;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;
import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.protol.RpcDataUtil;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.common.storage.Database;

/**
 * {@link Operator}
 *
 * @author maxuefeng
 * @since 2019/10/27
 */
public class TableOperator extends Operator {

    @Override
    public RpcData.S2C parse(RpcData.C2S requestInfo) throws Exception {

        RpcData.C2S.TableOperation tableOperation = requestInfo.getTableOperation();

        // 参数检验
        SnackExceptionUtil.checkNull(
                tableOperation,
                ParamIsNullException.class,
                String.format("tableOperation param must not null, tableOperation [ %s ] ", tableOperation));

        switch (tableOperation) {
            case dropTable: {
                ByteString dbInfoByStr = requestInfo.getDbInfo().getValue();
                ByteString tableInfoByStr = requestInfo.getTableInfo().getValue();

                SnackExceptionUtil.checkNull(ParamIsNullException.class, "tableInfoByStr or dbInfoByStr is null", dbInfoByStr, tableInfoByStr);

                DatabaseInfo databaseInfo = KryoHelper.deserialization(dbInfoByStr.toByteArray(), DatabaseInfo.class);
                TableInfo tableInfo = KryoHelper.deserialization(tableInfoByStr.toByteArray(), TableInfo.class);

                Database database = super.dbManagement.getDatabase(databaseInfo);
                boolean deleteResult = database.dropTable(tableInfo);

                if (deleteResult) {
                    return RpcDataUtil.defaultSuccess();
                } else {
                    return RpcDataUtil.defaultFailure(String.format("table is not exist ，table info [ %s ] ", tableInfo));
                }
            }
            case showTables: {
                ByteString dbInfoByStr = requestInfo.getDbInfo().getValue();

                // 参数检验
                SnackExceptionUtil.checkNull(
                        tableOperation,
                        ParamIsNullException.class,
                        String.format("tableOperation param must not null, tableOperation [ %s ] ", tableOperation));
                Database database = super.dbManagement.getDatabase(KryoHelper.deserialization(dbInfoByStr.toByteArray(), DatabaseInfo.class));
                return RpcDataUtil.defaultSuccess(Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(database.showTables()))).build());
            }
            case createTable: {
                ByteString dbInfoByStr = requestInfo.getDbInfo().getValue();
                ByteString tableInfoByStr = requestInfo.getTableInfo().getValue();

                SnackExceptionUtil.checkNull(ParamIsNullException.class, "tableInfoByStr or dbInfoByStr is null", dbInfoByStr, tableInfoByStr);

                TableInfo tableInfo = KryoHelper.deserialization(tableInfoByStr.toByteArray(), TableInfo.class);
                DatabaseInfo databaseInfo = KryoHelper.deserialization(dbInfoByStr.toByteArray(), DatabaseInfo.class);
                Database database = dbManagement.securityGetDatabase(databaseInfo);
                database.securityGetTable(tableInfo);

                return RpcDataUtil.defaultSuccess();
            }
            case modifyTable: {
            }
            default: {
                throw new SnackRuntimeException("unknown exception,cause rowOption is not match all option");
            }
        }
    }
}