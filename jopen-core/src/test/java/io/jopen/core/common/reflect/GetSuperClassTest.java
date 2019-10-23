package io.jopen.core.common.reflect;

import org.junit.Test;

import java.util.Date;
import java.util.stream.Stream;

/**
 * @author maxuefeng
 * @since 2019/10/23
 */
public class GetSuperClassTest {

    @Test
    public void testGetSuperClass(){
        System.err.println(Integer.class.getSuperclass());
        System.err.println(Integer.class.getGenericSuperclass());

        Stream.of(Integer.class.getInterfaces()).forEach(System.err::println);
        Stream.of(Date.class.getInterfaces()).forEach(System.err::println);

        Stream.of(Integer.class.getGenericInterfaces()).forEach(System.err::println);

    }
}
