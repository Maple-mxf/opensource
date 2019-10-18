package io.jopen.core.function.util;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author maxuefeng
 * @see com.google.common.base.Functions
 * @see Function
 * @see java.util.function.Function
 * @since 2019/10/17
 */
public class FunctionsTest {

    @Test
    public void simpleTestAPI() {
        // 默认调用toString
        Function<Object, String> stringFunction = Functions.toStringFunction();

        //
        Map<String, Integer> map = new HashMap<>();
        Function<String, Integer> mapFunction = Functions.forMap(map);

        Function<Object, Object> identityFunction = Functions.identity();

        Function<Object, Boolean> predicateFunction = Functions.forPredicate(Objects::nonNull);

        Function<Object, Integer> constantFunction = Functions.constant(2);

        System.err.println(Functions.toStringFunction().apply(1));
    }

    @Test
    public void testForMapFunction() {
        Map<String, Integer> map = new HashMap<>();
        map.put("k", 1);

        Function<String, Integer> forMapFunction = Functions.forMap(map);

        System.err.println(forMapFunction.apply("k"));
        BiMap<String, Objects> biMap = HashBiMap.create();
        Converter<Object, Object> converter = Maps.asConverter(biMap);

        // forMapFunction.andThen()
    }
}
