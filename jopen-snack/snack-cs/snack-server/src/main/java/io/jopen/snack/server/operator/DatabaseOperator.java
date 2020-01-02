package io.jopen.snack.server.operator;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.event.DatabaseEvent;
import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.exception.SnackRuntimeException;
import io.jopen.snack.common.protol.RpcData;
import io.jopen.snack.common.protol.RpcDataUtil;
import io.jopen.snack.common.serialize.KryoHelper;
import io.jopen.snack.server.PersistenceContext;

import java.util.Set;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class DatabaseOperator extends Operator {

    @Override
    public RpcData.S2C parse(RpcData.C2S requestInfo) throws Exception {

        RpcData.C2S.DBOperation dbOperation = requestInfo.getDbOperation();

        // 参数检验
        SnackExceptionUtil.checkNull(
                dbOperation,
                ParamIsNullException.class,
                String.format("dbOperation param must not null, DbOperation [ %s ] ", dbOperation));

        //
        switch (dbOperation) {
            case dropDB: {
                ByteString byteString = requestInfo.getDbInfo().getValue();
                SnackExceptionUtil.checkNull(byteString, ParamIsNullException.class, String.format("db" +
                        "database info must not null [ %s ] ", requestInfo));

                DatabaseInfo databaseInfo = KryoHelper.deserialization(byteString.toByteArray(), DatabaseInfo.class);
                boolean dropResult = super.dbManagement.dropDatabase(databaseInfo);

                if (dropResult) {
                    // 激活删除数据库事件
                    PersistenceContext.eventSource.fireEvent(new DatabaseEvent.Drop(databaseInfo));
                    return RpcDataUtil.defaultSuccess();
                } else {
                    return RpcDataUtil.defaultFailure(String.format("database [ %s ] is not exist", databaseInfo));
                }
            }
            case showDBs: {
                Set<String> databases = super.dbManagement.getDatabases();
                Any any = Any.newBuilder().setValue(ByteString.copyFrom(KryoHelper.serialization(databases))).build();
                return RpcDataUtil.defaultSuccess(any);
            }
            case createDB: {
                ByteString byteString = requestInfo.getDbInfo().getValue();
                SnackExceptionUtil.checkNull(byteString, ParamIsNullException.class, String.format("db" +
                        "database info must not null [ %s ] ", requestInfo));

                DatabaseInfo databaseInfo = KryoHelper.deserialization(byteString.toByteArray(), DatabaseInfo.class);
                try {
                    // 激活创建数据库事件
                    super.dbManagement.createDatabase(databaseInfo);
                    PersistenceContext.eventSource.fireEvent(new DatabaseEvent.Create(databaseInfo));
                    return RpcDataUtil.defaultSuccess();
                } catch (SnackRuntimeException ex) {
                    return RpcDataUtil.defaultFailure(ex.getMessage());
                }
            }
            case modifyDB: {

                //

            }
            default: {
            }
        }
        return null;
    }
}
