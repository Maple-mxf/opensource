package io.jopen.springboot.plugin.common.function;

import java.util.Map;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface PredicateCondition<T> {

    T test(Map<String, Object> args) throws Throwable;
}
