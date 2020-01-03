package io.jopen.core.common.collection;

import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * {@link java.util.stream.BaseStream}
 * {@link Stream}
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
     * @see Arrays#stream(Object[])  <pre>使用数组形式</pre>
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


    /**
     * <pre>foreach循环  属于terminal类型的  终结流</pre>
     *
     * @see Stream#forEach(Consumer)
     * @see Stream#forEachOrdered(Consumer)
     * @see Stream#unordered()
     * @see java.util.function.Consumer#accept(Object)
     */
    @Test
    public void testForeach() {
        List<String> list = new ArrayList<>();
        list.add("Type.Java");
        list.add("Object.Python");

        System.err.println(list);
        list.stream().filter(string -> string.contains("a")).forEach(System.err::println);
        list.stream().filter(string -> string.contains("p")).sorted().forEachOrdered(System.err::println);
    }


    /**
     * @see Stream#findFirst()  找到第一个元素
     * @see Stream#findAny()   找到任意一个元素
     */
    @Test
    public void testFindFirst() {
        List<String> list = new ArrayList<>();
        list.add("Type.Java");
        list.add("Object.Python");

        System.err.println(list);
        Optional<String> optional1 = list.stream().filter(string -> string.contains("a")).findFirst();
        Optional<String> optional2 = list.stream().filter(string -> string.contains("a")).findAny();

        optional1.ifPresent(System.err::println);
        optional2.ifPresent(System.err::println);
    }

    /**
     * @see Stream#skip(long)
     * @see Stream#limit(long)
     */
    @Test
    public void testSkipLimit() {
        List<String> list = new ArrayList<>();
        list.add("Type.Java");
        list.add("Object.Python");

        List<String> result = list.stream().skip(1L).limit(10).collect(Collectors.toList());
        System.err.println(result);
    }

    /**
     * @see Collectors#toList()
     * @see Collectors#toSet()
     * @see Collectors#toMap(Function, Function)
     * @see Collectors#toCollection(Supplier)
     * @see Collectors#toConcurrentMap(Function, Function)
     */
    @Test
    public void testCollect() {
        List<String> list = new ArrayList<>();
        list.add("Type.Java");
        list.add("Object.Python");

        List<String> result = list.stream().skip(1L).limit(10).collect(Collectors.toList());
    }

    /**
     * @see Stream#generate(Supplier)
     */
    @Test
    public void testGenerateStream() {
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.err::println);
    }

    class Student {
        public int age;
        public String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * @see Stream#mapToDouble(ToDoubleFunction)
     * @see Stream#count()
     * @see IntStream#sum()
     * @see Collectors#groupingBy(Function)
     * @see DoubleStream#average()
     */
    @Test
    public void testReduction() {
        Student tom = new Student(17, "Tom");
        Student jack = new Student(20, "Jack");

        // 大于18岁的学生统计
        long gt18Count = Stream.of(tom, jack).filter(student -> student.age > 18).count();

        // 年龄求和
        int ageSum = Stream.of(tom, jack).mapToInt(student -> student.age).sum();

        // 进行年龄求和
        double ageAverage = Stream.of(tom, jack).mapToDouble(student -> student.age).average().getAsDouble();

        // 根据年龄分组
        Map<Integer, List<Student>> groupByAgeResult = Stream.of(tom, jack).collect(Collectors.groupingBy(Student::getAge));

        System.err.println(gt18Count);
        System.err.println(ageAverage);
        System.err.println(ageSum);
        System.err.println(groupByAgeResult);
    }
}
