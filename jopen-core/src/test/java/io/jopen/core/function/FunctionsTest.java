package io.jopen.core.function;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author maxuefeng
 * @see java.util.function.Function
 * @see Functions
 */
public class FunctionsTest {

    @Test
    public void testSimpleAPI(){
        Function<Object, Object> identity = Functions.identity();

        Object a = identity.apply("a");

        System.out.println(a);

        Map<String, Integer> map = new HashMap<String, Integer>() {
            //构造一个测试用Map集合
            {
                put("love", 1);
                put("miss", 2);
            }
        };

        Function<String, Integer> function = Functions.forMap(map);

        Integer result = function.apply("love");

        System.err.println(result);

    }

    @Test
    public void testFunctionAPI(){
        java.util.function.Function<Object, Object> identity = java.util.function.Function.identity();


    }

    @Test
    public void testOptionalAPI(){
        List<String> collection = ImmutableList.of("Streaming and offline");
        Optional<String> optional = collection.stream().filter(s -> s.contains("off")).findAny();
        optional.ifPresent(s -> {
            String substring = s.substring(1);
            System.err.println(substring);
        });
    }
}
