package io.jopen.core.algorithm.sort;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @author maxuefeng [m17793873123@163.com]
 */
public class SortAlgorithmTest {

    // TODO PASS  param: result  2,23,45,56
    @Test
    public void testBubbleSort() {

        int[] array = new BubbleSortAlgorithm().sort(new int[]{23, 2, 56, 45});

        for (int i1 : array) {

            System.out.print("," + i1);
        }
    }

    @Test
    public void testSelectionSort() {
        int[] array = new SelectSortAlgorithm().sort(new int[]{23, 2, 56, 45});

        for (int i1 : array) {
            System.out.println(i1);
        }
    }

    @Test
    public void test() {

        Map<Integer, Double> map = Maps.newHashMap();

        map.put(1, 10.0);

//        map.entrySet().stream().map(entry -> entry.getValue() * 0.8).;

        map.forEach((k, v) -> map.put(k, v *= 0.8));

        System.out.println(map);
    }
}
