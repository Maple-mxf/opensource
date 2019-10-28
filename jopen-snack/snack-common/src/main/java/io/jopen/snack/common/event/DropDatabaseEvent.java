package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class DropDatabaseEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    public DropDatabaseEvent(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }
}
