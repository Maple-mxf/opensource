package io.jopen.core.common.util;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 */
public class CollectionTest {

    @Test
    public void testOperationCollection() {

        Map<String, Integer> var = new HashMap<>();

        var.put("k1", 89);
        var.put("k2", 999);

        Set<Map.Entry<String, Integer>> result = var.entrySet()
                .stream()
                .filter(e -> e.getValue() > 89)   // V > 89
                .peek(entry -> entry.setValue(entry.getValue() * 10))   // V*10
                .collect(Collectors.toSet());

        Set<String> set = var.keySet();
        Map<String, Integer> var1 = new HashMap<>();
        for (String v : set) {
            if (var.get(v) > 89) {
                var1.put(v, var.get(v) * 10);
            }
        }

        result.forEach(v -> System.out.println(v.getKey() + "  " + v.getValue()));
    }

    /**
     * 测试在Queue中存储元素  是否显示queue的size>0 并且恒等于null
     */
    @Test
    public void testPutNull2Queue() {

        Queue<Object> queue = new LinkedList<>();

        queue.offer(null);
        queue.offer(null);
        queue.offer(null);
        queue.offer(null);

        System.err.println(queue.size());

        while (!queue.isEmpty()){

            if (queue.poll() != null){

                System.err.println("....");
            }else{

                System.err.println("??????");
            }
        }


    }
}
