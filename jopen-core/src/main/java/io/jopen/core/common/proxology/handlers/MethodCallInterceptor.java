package io.jopen.core.common.proxology.handlers;

import java.lang.reflect.Method;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface MethodCallInterceptor {

    /**
     * 方法调用拦截器
     *
     * @param proxy
     * @param method
     * @param args
     * @param handler
     * @return
     * @throws Throwable
     */
    Object intercept(Object proxy, Method method, Object[] args, MethodCallHandler handler) throws Throwable;

    default MethodCallHandler intercepting(Method method, MethodCallHandler handler) {
        return (proxy, args) -> intercept(proxy, method, args, handler);
    }
}
