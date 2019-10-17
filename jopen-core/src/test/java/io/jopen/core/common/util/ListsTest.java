package io.jopen.core.common.util;

import io.jopen.core.common.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author maxuefeng
 * @see io.jopen.core.common.Lists
 */
public class ListsTest {

    @Test
    public void simpleTest(){
        List<Integer> integers = Lists.of(1, 2, 4, 6);

        System.err.println(integers);
    }
}
