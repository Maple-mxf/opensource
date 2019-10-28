package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.protol.RpcData;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class DatabaseEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    private RpcData.C2S.DBOperation dbOperation;

    DatabaseEvent(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation) {
        this.databaseInfo = databaseInfo;
        this.dbOperation = dbOperation;
    }

    public static class Create extends DatabaseEvent {
        public Create(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation) {
            super(databaseInfo, dbOperation);
        }
    }

    public static class Drop extends DatabaseEvent {
        public Drop(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation) {
            super(databaseInfo, dbOperation);
        }
    }

    public static class Modify extends DatabaseEvent {
        public Modify(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation) {
            super(databaseInfo, dbOperation);
        }
    }
}
