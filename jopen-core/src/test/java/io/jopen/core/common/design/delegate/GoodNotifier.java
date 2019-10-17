package io.jopen.core.common.design.delegate;

import java.lang.reflect.InvocationTargetException;

/**
 *
 *
 * @author maxuefeng
 */
public class GoodNotifier extends Notifier {

    @Override
    public void addListener(Object obj, String methodName, Object... params) throws NoSuchMethodException {
        System.err.println("[INFO] add listener...");

        this.getEventHandler().addEvent(new Event(obj, methodName, params));
    }

    @Override
    public void notifyOther() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.getEventHandler().notifyOther();
    }
}
