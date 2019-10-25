package io.jopen.leopard.base.storage.serialize;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/25
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
}

