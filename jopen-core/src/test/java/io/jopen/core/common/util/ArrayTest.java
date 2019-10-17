package io.jopen.core.common.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maxuefeng
 */
public class ArrayTest {

    @Test
    public void testList2Array() {

        int[] temp = new int[3];

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            list.add(i);
        }

        Object[] objects =  list.toArray();

        System.err.println(objects);
    }
}
