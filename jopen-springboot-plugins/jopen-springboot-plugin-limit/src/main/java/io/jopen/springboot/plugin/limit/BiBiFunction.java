package io.jopen.springboot.plugin.limit;

/**
 * @author maxuefeng
 * @see java.util.function.BiFunction
 * @since 2020/2/5
 */
@FunctionalInterface
public interface BiBiFunction<ONE, TWO, THREE, FOUR, R> {
    R apply(ONE one, TWO two, THREE three, FOUR four);
}
