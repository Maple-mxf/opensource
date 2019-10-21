package io.jopen.core.function.predicate;

import java.util.function.Consumer;

/**
 * @author maxuefeng
 * @since 2019-04-28
 * @see com.google.common.base.Predicates
 * @see java.util.function.Predicate
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
