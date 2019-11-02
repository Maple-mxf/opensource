package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class DatabaseEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    @Nullable
    private DatabaseInfo modifyDBInfo;

    DatabaseEvent(@NonNull DatabaseInfo databaseInfo) {
        this(databaseInfo, null);
    }

    DatabaseEvent(@NonNull DatabaseInfo databaseInfo,
                  @Nullable DatabaseInfo modifyDBInfo) {
        this.databaseInfo = databaseInfo;
        this.modifyDBInfo = modifyDBInfo;
    }


    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public void setDatabaseInfo(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }


    public static class Create extends DatabaseEvent {
        public Create(DatabaseInfo databaseInfo) {
            super(databaseInfo);
        }

        public Create(@NonNull DatabaseInfo databaseInfo,
                      @Nullable DatabaseInfo targetDatabaseInfo) {
            super(databaseInfo, targetDatabaseInfo);
        }
    }

    public static class Drop extends DatabaseEvent {
        public Drop(@NonNull DatabaseInfo databaseInfo) {
            super(databaseInfo);
        }
    }

    public static class Modify extends DatabaseEvent {
        public Modify(@NonNull DatabaseInfo databaseInfo,
                      @NonNull DatabaseInfo targetDatabaseInfo) {
            super(databaseInfo, targetDatabaseInfo);
        }
    }
}
