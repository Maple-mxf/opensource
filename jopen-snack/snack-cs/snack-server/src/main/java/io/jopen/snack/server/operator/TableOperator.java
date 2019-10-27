package io.jopen.snack.server.operator;

import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.protol.RpcData;

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
            }
            case showTables: {
            }
            case createTable: {
            }
            case modifyTable: {
            }
        }
        return null;
    }
}
