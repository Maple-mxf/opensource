package io.jopen.snack.server.operator;

import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.protol.RpcData;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class DatabaseOperator extends Operator {

    @Override
    public RpcData.S2C parse(RpcData.C2S requestInfo) throws Exception {

        RpcData.C2S.DBOperation dbOperation = requestInfo.getDbOperation();

        // 参数检验
        SnackExceptionUtil.checkNull(dbOperation, ParamIsNullException.class, String.format("dbOperation param must not null, DbOperation [ %s ] ", requestInfo.getDbOperation()));

        //
        switch (dbOperation) {
            case dropDB: {

                super.dbManagement.

            }
            case showDBs: {
            }
            case createDB: {
            }
            case modifyDB: {
            }
            default: {

            }
        }

        return null;
    }
}
