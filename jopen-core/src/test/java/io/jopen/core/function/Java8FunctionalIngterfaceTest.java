package io.jopen.core.function;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2019/10/13
 * <p>
 * {@link FunctionalInterface}
 */
public class Java8FunctionalIngterfaceTest {

    /**
     * 聚合操作：group by   sum
     *
     * @see Function  生产者
     * @see java.util.function.Consumer  消费者
     * @see java.util.function.Supplier  另一种含义的生产者   一般用于固定返回
     * @see java.util.stream.Stream
     * @see java.util.function.BiConsumer  double    Bi->
     * @see java.util.function.BiFunction  double
     * @see java.util.function.Predicate
     */
    @Test
    public void testProducer() {

        ImmutableList<String> list = ImmutableList.of("HelloWorld");

        // MapReduce  Hadoop简称MR
        Set<Object> set = list.stream()
                .map((Function<String, Object>) String::toUpperCase)
                .collect(Collectors.toSet());

        System.err.println(set);
    }


}
