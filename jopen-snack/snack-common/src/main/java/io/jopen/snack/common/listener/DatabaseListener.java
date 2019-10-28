package io.jopen.snack.common.listener;

import io.jopen.snack.common.event.DatabaseEvent;
import io.jopen.snack.common.event.SnackApplicationEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class DatabaseListener extends SnackApplicationListener {

    public static class Create extends DatabaseListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof DatabaseEvent.Create) {
                DatabaseEvent createEvent = (DatabaseEvent) event;
                this.submit(() -> {
                    // 创建数据库
                    super.dbManagement.createDatabase(createEvent.getDatabaseInfo());
                    return Boolean.TRUE;
                }, null);
            }
        }
    }

    public static class Drop extends SnackApplicationListener {
        @Override
        public void apply(@NonNull SnackApplicationEvent event) {
            if (event instanceof DatabaseEvent.Drop) {
                DatabaseEvent createEvent = (DatabaseEvent) event;
                this.submit(() -> {
                    // 创建数据库
                    super.dbManagement.createDatabase(createEvent.getDatabaseInfo());
                    return Boolean.TRUE;
                }, null);
            }
        }
    }
}
