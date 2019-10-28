package io.jopen.snack.common.event;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.TableInfo;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class CreateTableEvent implements SnackApplicationEvent {

    private DatabaseInfo databaseInfo;

    private TableInfo tableInfo;

    public CreateTableEvent(DatabaseInfo databaseInfo, TableInfo tableInfo) {
        this.databaseInfo = databaseInfo;
        this.tableInfo = tableInfo;
    }



}
