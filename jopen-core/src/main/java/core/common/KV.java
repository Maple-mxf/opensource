package core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maxuefeng
 */
public class KV {


    private KV() {
    }


    public static <K, V> Map<K, V> of() {
        return of(null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        return of(k1, v1, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        return of(k1, v1, k2, v2, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return of(k1, v1, k2, v2, k3, v3, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10, K k11, V v11) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10, k11, v11, null, null);
    }


    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10, K k11, V v11, K k12, V v12) {
        return of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10, k11, v11, k12, v12, null, null);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6,
                                      K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10, K k11, V v11, K k12, V v12, K k13, V v13) {

        HashMap<K, V> map = new HashMap<>();

        putIfNotNull(map, k1, v1);
        putIfNotNull(map, k2, v2);
        putIfNotNull(map, k3, v3);
        putIfNotNull(map, k4, v4);
        putIfNotNull(map, k5, v5);
        putIfNotNull(map, k6, v6);
        putIfNotNull(map, k7, v7);
        putIfNotNull(map, k8, v8);
        putIfNotNull(map, k9, v9);
        putIfNotNull(map, k10, v10);
        putIfNotNull(map, k11, v11);
        putIfNotNull(map, k12, v12);
        putIfNotNull(map, k13, v13);
        return map;
    }

    /**
     * @param key
     * @param value
     */
    private static <K, V> void putIfNotNull(Map<K, V> v, K key, V value) {
        if (key != null) {
            v.put(key, value);
        }
    }
}
