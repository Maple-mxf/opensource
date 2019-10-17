package io.jopen.core.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON指针是RFC 6901为JSON文档定义的简单查询语言。
 * 简而言之，JSONPointer允许用户使用字符串导航到JSON文档，并检索目标对象，如XPATH的简单形式。
 * 路径段由'/'字符分隔，当字符串显示为字符串的第一个字符时，它表示文档的根。
 * 使用序数导航数组元素，从0开始计数.JSONPointer字符串可以扩展到任意数量的段。
 * 如果导航成功，则返回匹配的项目。匹配的项可以是JSONObject，
 * JSONArray或JSON值。如果JSONPointer字符串构建失败，则抛出相应的异常。
 * 如果导航无法找到匹配项，则抛出JSONPointerException。
 *
 * @author maxuefeng
 */
public class Json extends com.alibaba.fastjson.JSONObject {


    /**
     * @return
     */
    public static com.alibaba.fastjson.JSONObject of() {
        return new com.alibaba.fastjson.JSONObject();
    }

    /**
     * @param jsonString
     * @return
     */
    public static com.alibaba.fastjson.JSONObject of(String jsonString) {
        return com.alibaba.fastjson.JSONObject.parseObject(jsonString);
    }

    /**
     * @param m
     * @return
     */
    public static com.alibaba.fastjson.JSONObject of(Map<String, Object> m) {
        return new com.alibaba.fastjson.JSONObject(m);
    }

    public static com.alibaba.fastjson.JSONObject of(ImmutableMap<String, Object> m) {
        return new com.alibaba.fastjson.JSONObject(m);
    }

    /**
     * @param bean
     * @return
     */
    public static Object of(Object bean) {
        return JSON.toJSON(bean);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1) {
        return of(
                k1, v1,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2) {
        return of(
                k1, v1,
                k2, v2,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3) {
        return of(k1, v1,
                k2, v2,
                k3, v3,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8) {
        return of(k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                null, null);
    }


    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10) {
        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                k10, v10,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10,
                                                     String k11, Object v11) {

        return of(k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                k10, v10,
                k11, v11,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10,
                                                     String k11, Object v11,
                                                     String k12, Object v12) {

        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                k10, v10,
                k11, v11,
                k12, v12,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10,
                                                     String k11, Object v11,
                                                     String k12, Object v12,
                                                     String k13, Object v13) {

        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                k10, v10,
                k11, v11,
                k12, v12,
                k13, v13,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10,
                                                     String k11, Object v11,
                                                     String k12, Object v12,
                                                     String k13, Object v13,
                                                     String k14, Object v14) {

        return of(
                k1, v1,
                k2, v2,
                k3, v3,
                k4, v4,
                k5, v5,
                k6, v6,
                k7, v7,
                k8, v8,
                k9, v9,
                k10, v10,
                k11, v11,
                k12, v12,
                k13, v13,
                k14, v14,
                null, null);
    }

    public static com.alibaba.fastjson.JSONObject of(String k1, Object v1,
                                                     String k2, Object v2,
                                                     String k3, Object v3,
                                                     String k4, Object v4,
                                                     String k5, Object v5,
                                                     String k6, Object v6,
                                                     String k7, Object v7,
                                                     String k8, Object v8,
                                                     String k9, Object v9,
                                                     String k10, Object v10,
                                                     String k11, Object v11,
                                                     String k12, Object v12,
                                                     String k13, Object v13,
                                                     String k14, Object v14,
                                                     String k15, Object v15) {

        Map<String, Object> map = new HashMap<>();

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
        putIfNotNull(map, k14, v14);
        putIfNotNull(map, k15, v15);

        com.alibaba.fastjson.JSONObject r = of();
        r.putAll(map);

        return r;
    }

    /**
     * @param key
     * @param value
     */
    private static void putIfNotNull(Map<String, Object> v, String key, Object value) {
        if (key != null && value != null) {
            v.put(key, value);
        }
    }

    /**
     * @param target
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T toJavaObject(Class<T> target, String json) {
        return JSONObject.toJavaObject(JSON.parseObject(json), target);
    }
}






















