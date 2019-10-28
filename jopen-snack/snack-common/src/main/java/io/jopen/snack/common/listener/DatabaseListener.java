package io.jopen.snack.common.listener;

import io.jopen.snack.common.event.DatabaseEvent;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.exception.ParamIsNullException;
import io.jopen.snack.common.exception.SnackExceptionUtil;
import io.jopen.snack.common.protol.RpcData;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class DatabaseListener extends SnackApplicationListener {

    public static class Create extends DatabaseListener {
        @Override
        public void handEvent(@NonNull SnackApplicationEvent event) {
            if (event instanceof DatabaseEvent.Create) {
                DatabaseEvent createEvent = (DatabaseEvent) event;
                this.submit(() -> {
                    RpcData.C2S.DBOperation dbOperation = createEvent.getDbOperation();
                    SnackExceptionUtil.checkNull(dbOperation, ParamIsNullException.class, "数据库操作符不可为空");

                    switch (createEvent.getDbOperation()) {
                        case modifyDB: {
                        }
                        case createDB: {
                        }
                        case showDBs: {
                        }
                        case dropDB: {
                        }
                        default: {
                        }
                    }
                    return null;
                }, null);
            }
        }
    }

    public static class Drop extends SnackApplicationListener {
        @Override
        public void handEvent(@NonNull SnackApplicationEvent event) {

        }
    }
}
