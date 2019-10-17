package io.jopen.core.common.design.delegate;

import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/**
 * 通知者类完全不知道自己需要通知的是谁，
 * 做到了完全解耦，同时也去掉了抽象的观察者类
 * 观察者模式的缺点是抽象通知者依赖抽象观察者
 * 中用委托技术处理这个问题，事件委托扩展性好
 *
 * @author maxuefeng
 * @see Array#newInstance(Class, int)
 */
public class DelegateTest {

    @Test
    public void simpleTestDelegate()
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {

        Listener listener = new Listener();

        Object r = Array.newInstance(String.class, 1);
        Array.set(r, 0, "Hello Ketty!");

        Notifier notifier = new GoodNotifier();
        notifier.addListener(listener, "doSomethings", r);

        notifier.notifyOther();
    }


}
