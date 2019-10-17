package io.jopen.core.common.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 */
public class StreamTest {

    @Test
    public void testSimpleAPI(){
       // Arrays.stream(null)
        format("",null);
    }

    public static void format(String origin, Character[] excludeChars) {
        List<Character> collect = Arrays.stream(excludeChars).collect(Collectors.toList());

        System.err.println(collect);
    }
}
