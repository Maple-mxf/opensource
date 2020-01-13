package io.jopen.orm.hbase.query.criterion;

/**
 * An Serializable function{@link java.util.function.Function}
 *
 * @author maxuefeng
 * @since 2020-01-13
 */
public interface SFunction<I, O> extends java.io.Serializable {
    O apply(I input);
}
