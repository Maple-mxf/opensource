package io.jopen.core.common.proxology;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * {@link InvocationHandler}
 *
 * @author maxuefeng
 */
public class PassThroughInvocationHandler implements InvocationHandler {

    /**
     * @param target 目标对象
     * @param iface  类型
     * @param <T>    目标对象的类型
     * @return 返回一个代理对象
     * @see Proxy
     */
    @SuppressWarnings("unchecked")
    public static <T> T proxying(T target, Class<T> iface) {
        return (T) Proxy.newProxyInstance(
                iface.getClassLoader(),
                new Class<?>[]{iface},
                new PassThroughInvocationHandler(target));
    }

    private final Object target;

    /**
     * @param target
     */
    public PassThroughInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(target, args);
    }
}
