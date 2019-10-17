package io.jopen.core.common.design.delegate;

import java.lang.reflect.InvocationTargetException;

/**
 * @author maxuefeng
 */
public abstract class Notifier {

    private EventHandler eventHandler = new EventHandler();


    /**
     * 增加监听器
     *
     * @param obj        要执行方法的对象
     * @param methodName 要执行的方法
     * @param params     要执行的方法的参数
     */
    public abstract void addListener(Object obj, String methodName, Object... params) throws NoSuchMethodException;

    public abstract void notifyOther() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
