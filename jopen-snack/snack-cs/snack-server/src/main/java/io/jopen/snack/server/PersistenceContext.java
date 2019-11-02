package io.jopen.snack.server;

import io.jopen.snack.common.SnackEventSource;
import io.jopen.snack.common.event.SnackApplicationEvent;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public class PersistenceContext {

    public static final SnackEventSource eventSource = SnackEventSource.SNACK_EVENT_SOURCE;

    public static void addEvent(Class clazz) {
        try {
            Object eventInstance = clazz.getConstructor().newInstance();
            eventSource.fireEvent((SnackApplicationEvent) eventInstance);
        } catch (Exception ignored) {
        }
    }
}
