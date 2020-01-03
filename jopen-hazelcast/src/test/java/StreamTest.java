import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link java.util.stream.BaseStream}
 * {@link java.util.stream.Stream}
 *
 * @author maxuefeng
 * @since 2020-01-03
 */
public class StreamTest {


    /**
     * <p>创建流的几种方式</p>
     *
     * @see Collection#stream()  <pre>创建串行流</pre>
     * @see Collection#parallelStream() <pre>创建并行流</pre>
     * @see Stream#parallel() <pre>并行流转换</pre>
     * @see java.util.Arrays#stream(Object[])  <pre>使用数组形式</pre>
     * @see Stream#of(Object[])  <pre>直接创建</pre>
     */
    @Test
    public void testCreateStream() {

        // 直接创建
        Set<String> set1 = Stream.of(1, 2, 3, 4).map(String::valueOf).collect(Collectors.toSet());

        // 通过集合创建
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Set<String> set2 = list.stream().map(String::valueOf).collect(Collectors.toSet());

        // 通过数组创建
        Set<String> set3 = Arrays.stream(new int[]{1, 2, 3, 4}).mapToObj(String::valueOf).collect(Collectors.toSet());
    }

    /**
     * <pre>泛型</pre>
     * <? super T, ? extends R>
     *
     * @see java.util.function.Function
     */
    @Test
    public void testMapFunction() {
        List<String> list = new ArrayList<>();

        list.add("Java");
        list.add("Python");
        list.add("Scala");
        list.add("Golang");

        System.err.println(list);
        List<String> result1 = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.err.println(result1);
    }

    /**
     * <pre>泛型</pre>
     * <? super T, ? extends Stream<? extends R>
     *
     * @see java.util.function.Function#apply(Object)
     */
    @Test
    public void testFlatMapFunction() {
        List<String> list = new ArrayList<>();

        list.add("Type.Java");
        list.add("Object.Python");

        System.err.println(list);
        List<String> result1 = list.stream().flatMap(string -> {
            String[] strArr = string.split("\\.");
            return Stream.of(strArr);
        }).collect(Collectors.toList());
        System.err.println(result1);
    }

    /**
     * <pre>return 返回值为Boolean</pre>
     *
     * @see java.util.function.Predicate#test(Object)
     */
    @Test
    public void testPredicate() {
        List<String> list = new ArrayList<>();

        list.add("Type.Java");
        list.add("Object.Python");

        System.err.println(list);

        List<String> result = list.stream().filter(string -> string.contains("a")).collect(Collectors.toList());
        System.err.println(result);
    }

    /**
     * @see Optional#ifPresent(Consumer)
     * @see BinaryOperator#apply(Object, Object)
     */
    @Test
    public void testReduce() {
        List<String> list = new ArrayList<>();

        list.add("Type.Java");
        list.add("Object.Python");

        System.err.println(list);
        Optional<String> optional = list.stream().reduce((s, s2) -> s + s2);
        optional.ifPresent(System.err::println);
    }
}
