package io.jopen.core.common.proxology.memoization;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * {@link Function}
 *
 * @author maxuefeng
 */
public final class Memoizer {

    private Memoizer() {
    }

    /**
     * @param mappingFunction
     * @param <I>
     * @param <O>
     * @return
     * @see java.util.Map#computeIfPresent(Object, BiFunction)
     */
    public static <I, O> Function<I, O> memoize(Function<I, O> mappingFunction) {
        ConcurrentMap<I, O> cache = new ConcurrentHashMap<>();
        return input -> cache.computeIfAbsent(input, mappingFunction);
    }

}
