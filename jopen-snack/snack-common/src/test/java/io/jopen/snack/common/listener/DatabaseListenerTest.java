package io.jopen.snack.common.listener;

import io.jopen.snack.common.DatabaseInfo;
import io.jopen.snack.common.SnackEventSource;
import io.jopen.snack.common.event.DatabaseEvent;
import io.jopen.snack.common.protol.RpcData;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class DatabaseListenerTest {
    private SnackEventSource snackEventSource = new SnackEventSource();

    /**
     * @see SnackApplicationListener
     */
    @Before
    public void before() {
        DatabaseListener.Create listener = new DatabaseListener.Create();
        listener.start();
        snackEventSource.registerListener(listener);
        // 启动注册器

    }

    @Test
    public void testCreateDB() throws InterruptedException {
        DatabaseInfo databaseInfo = new DatabaseInfo("snackDB1.0", System.currentTimeMillis());
        snackEventSource.fireEvent(new DatabaseEvent.Create(databaseInfo, RpcData.C2S.DBOperation.createDB));
        TimeUnit.SECONDS.sleep(1000);
    }
}
