package io.jopen.springboot.plugin.common.function;

import java.util.function.Consumer;

/**
 * @author maxuefeng
 * @since 2019-04-28
 */
@FunctionalInterface
public interface PredicateThen<T> {

    /**
     * @param t
     * @return
     */
    boolean test(T t);

    default void andThen(Consumer<T> after) {

    }
}
