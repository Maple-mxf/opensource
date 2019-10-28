package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.protol.RpcData;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class DatabaseEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    private RpcData.C2S.DBOperation dbOperation;

    @Nullable
    private DatabaseInfo modifyDBInfo;

    DatabaseEvent(@NonNull DatabaseInfo databaseInfo,
                  RpcData.C2S.DBOperation dbOperation) {
        this(databaseInfo, dbOperation, null);
    }

    DatabaseEvent(@NonNull DatabaseInfo databaseInfo,
                  RpcData.C2S.DBOperation dbOperation,
                  @Nullable DatabaseInfo modifyDBInfo) {
        this.databaseInfo = databaseInfo;
        this.dbOperation = dbOperation;
        this.modifyDBInfo = modifyDBInfo;
    }


    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }

    public RpcData.C2S.DBOperation getDbOperation() {
        return dbOperation;
    }

    public void setDbOperation(RpcData.C2S.DBOperation dbOperation) {
        this.dbOperation = dbOperation;
    }


    public static class Create extends DatabaseEvent {
        public Create(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation) {
            super(databaseInfo, dbOperation);
        }

        public Create(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation, DatabaseInfo targetDatabaseInfo) {
            super(databaseInfo, dbOperation, targetDatabaseInfo);
        }
    }

    public static class Drop extends DatabaseEvent {
        public Drop(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation, DatabaseInfo targetDatabaseInfo) {
            super(databaseInfo, dbOperation, targetDatabaseInfo);
        }
    }

    public static class Modify extends DatabaseEvent {
        public Modify(DatabaseInfo databaseInfo, RpcData.C2S.DBOperation dbOperation, DatabaseInfo targetDatabaseInfo) {
            super(databaseInfo, dbOperation, targetDatabaseInfo);
        }
    }
}
