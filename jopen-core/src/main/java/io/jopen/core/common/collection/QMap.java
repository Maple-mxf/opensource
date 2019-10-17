package io.jopen.core.common.collection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author maxuefeng
 */
public class QMap {

    /**
     *
     * @param objects
     * @return
     */
    public static Map of(Object... objects) {

        Map<Object, Object> m = new HashMap<>();

        // 数据长度不是偶数
        if (objects.length % 2 != 0) {
            return m;
        }

        // 数据长度是偶数
        Iterator<Object> iterator = Arrays.asList(objects).iterator();

        while (iterator.hasNext()) {

            // 获取K
            Object K = iterator.next();
            // 获取V
            Object V = iterator.next();

            m.put(K, V);
        }

        return m;
    }

    /**
     * @param k
     * @param v
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> of(K k, V v) {
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7,
                                      K k8, V v8
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7,
                                      K k8, V v8,
                                      K k9, V v9
                                      ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7,
                                      K k8, V v8,
                                      K k9, V v9,
                                      K k10, V v10
                                      ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        map.put(k10, v10);
        return map;
    }


    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7,
                                      K k8, V v8,
                                      K k9, V v9,
                                      K k10, V v10,
                                      K k11, V v11
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        map.put(k10, v10);
        map.put(k11, v11);
        return map;
    }

    public static <K, V> Map<K, V> of(K k1, V v1,
                                      K k2, V v2,
                                      K k3, V v3,
                                      K k4, V v4,
                                      K k5, V v5,
                                      K k6, V v6,
                                      K k7, V v7,
                                      K k8, V v8,
                                      K k9, V v9,
                                      K k10, V v10,
                                      K k11, V v11,
                                      K k12, V v12
    ) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        map.put(k4, v4);
        map.put(k5, v5);
        map.put(k6, v6);
        map.put(k7, v7);
        map.put(k8, v8);
        map.put(k9, v9);
        map.put(k10, v10);
        map.put(k11, v11);
        map.put(k12, v12);
        return map;
    }
}
