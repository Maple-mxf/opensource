package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class CreateDatabaseEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    public CreateDatabaseEvent(DatabaseInfo databaseInfo) {
        this.databaseInfo = databaseInfo;
    }
}
