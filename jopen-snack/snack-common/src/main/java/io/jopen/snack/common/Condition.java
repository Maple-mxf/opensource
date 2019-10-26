package io.jopen.snack.common;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
@FunctionalInterface
public interface Condition {
    boolean test(Object row);
}
