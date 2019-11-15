package io.jopen.mapreduce.javastream;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

/**
 * @author maxuefeng
 * @since 2019/11/13
 */
public class Java8StreamMapReduce {

    /**
     * 针对于字符串元素进行相加
     *
     * @param collection
     */
    private void mapReduceStr(Collection<Object> collection) {
        String reduceResult = collection.stream()
                .map(Object::toString)
                .reduce("",
                        (s, s2) -> s.toUpperCase() + s2.toUpperCase(), // Funtion
                        (s, s2) -> {
                            System.err.println(s);
                            System.err.println(s2);
                            return s2;
                        });                            // Binary

        System.err.println(reduceResult);
    }

    /**
     * 针对于数字求平均值
     *
     * @param numbers
     */
    private void mapReduceNum(Collection<Number> numbers) {
        double mrResult = numbers.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
        System.err.println(mrResult);
    }

    public static void main(String[] args) {
        Java8StreamMapReduce mapReduce = new Java8StreamMapReduce();

        // mr 字符串
        mapReduce.mapReduceStr(ImmutableList.of("HelloSpark", "HelloRedis", "HelloFlink"));

        // mr数字
        mapReduce.mapReduceNum(ImmutableList.of(1, 4, 1));

    }
}
