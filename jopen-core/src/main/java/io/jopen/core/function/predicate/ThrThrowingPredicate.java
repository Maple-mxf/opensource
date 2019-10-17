package io.jopen.core.function.predicate;

import java.util.Map;

/**
 * @author maxuefeng
 * @since 2019-04-28
 */
@FunctionalInterface
public interface ThrThrowingPredicate<T,U,K> {

    Map.Entry<Boolean, Object> test(T t, U u, K k) throws Throwable;
}
