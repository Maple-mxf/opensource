package io.jopen.snack.common;

import java.io.Serializable;

/**
 * @author maxuefeng
 * @since 2019/10/26
 */
@FunctionalInterface
public interface Condition extends Serializable {
    boolean test(Object row);
}
