package core.common.proxology.proxies;


import io.jopen.core.common.proxology.EqualisableByState;
import io.jopen.core.common.proxology.handlers.MethodCallInterceptor;
import io.jopen.core.common.proxology.handlers.MethodInterpreters;
import io.jopen.core.common.proxology.handlers.PropertyValueStore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.stream.Stream;

import static io.jopen.core.common.proxology.handlers.MethodInterpreters.*;

/**
 * @author maxuefeng
 */
public final class Proxies {

    @SuppressWarnings("unchecked")
    public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>...otherIfaces) {
        Class<?>[] allInterfaces = Stream.concat(
                Stream.of(iface),
                Stream.of(otherIfaces))
                .distinct()
                .toArray(Class<?>[]::new);

        return (T) Proxy.newProxyInstance(iface.getClassLoader(),
                allInterfaces,
                handler);
    }

    public static <T> T interceptingProxy(T target, Class<T> iFace, MethodCallInterceptor interceptor) {
        return simpleProxy(iFace,
                caching(intercepting(
                        handlingDefaultMethods(MethodInterpreters.binding(target)),
                        interceptor)));
    }

    public static <T> T propertyMapping(Class<? extends T> iFace, Map<String, Object> propertyValues) {

        PropertyValueStore store = new PropertyValueStore(iFace, propertyValues);
        return simpleProxy(iFace, store.createMethodInterpreter(), EqualisableByState.class);
    }

}
