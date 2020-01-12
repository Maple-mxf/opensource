package io.jopen.orm.hbase.query.criterion;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

/**
 * 从String解析出一个符号对象
 */
public class SymbolicLookup implements Function<Symbolic, String> {

    @Override
    public String apply(Symbolic symbolic) {
        return symbolic.symbol();
    }

    public static <T extends Symbolic> ImmutableMap<String, T> map(T[] values) {
        java.util.function.Function<Symbolic, @Nullable String> symbolicLookup = new SymbolicLookup();
        return Maps.uniqueIndex(Arrays.asList(values), symbolicLookup::apply);
    }

    public static <T extends Symbolic> T resolve(String symbol, ImmutableMap<String, T> map, Class<T> clss) {
        T symbolic = map.get(symbol);
        if (symbolic == null) {
            throw new IllegalArgumentException(symbol + " is not a valid " + clss.getSimpleName() + " symbol");
        }
        return symbolic;
    }

}
