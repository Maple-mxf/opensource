package io.jopen.snack.common;

import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.listener.SnackApplicationListener;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class SnackEventSource {

    //保存监听器的列表，类似观察者模式中保存所有观察者的集合；子类可以保存自己的监听器。
    private Collection<SnackApplicationListener> listeners = new CopyOnWriteArraySet<>();

    //注册监听器
    public void registerListener(SnackApplicationListener listener) {
        this.listeners.add(listener);
    }

    //删除监听
    public void removeListener(SnackApplicationListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * 根据触发的条件进行事件的执行
     *
     * @param event 触发条件
     */
    public void fireEvent(SnackApplicationEvent event) {
        notifyListener(event);
    }

    /**
     * 事件具体的执行
     *
     * @param event 定义的事件
     */
    public void notifyListener(SnackApplicationEvent event) {

        for (SnackApplicationListener evt : listeners) {
            //实例监听器对象，并调用监听器的方法
            evt.handEvent(event);
        }
    }

}
