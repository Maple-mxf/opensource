package io.jopen.snack.common.event;

import io.jopen.snack.common.SnackEventSource;
import io.jopen.snack.common.listener.table.CreateTableEventListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class EventTest {

    private SnackEventSource snackEventSource = new SnackEventSource();

    private CreateTableEventListener createTableEventListener = new CreateTableEventListener();

    @Before
    public void before() {
        snackEventSource.registerListener(createTableEventListener);
    }

    @Test
    public void testSimple() {
        snackEventSource.fireEvent(new CreateDatabaseEvent());
    }
}