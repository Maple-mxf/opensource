package io.jopen.springboot.plugin.mongo.template.builder;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author maxuefeng
 * @see java.util.function.Function
 * @since 2020/1/8
 */
@FunctionalInterface
public interface SFunction<I, O> extends java.io.Serializable {
    O apply(@NonNull I input);
}
