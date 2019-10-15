package core.common.json;


import com.alibaba.fastjson.JSONArray;

import java.util.*;


/**
 * @author maxuefeng
 * @see com.alibaba.fastjson.JSONArray
 */
public final class JsonArray {

    private JsonArray() {

    }

    public static JSONArray of() {
        return new JSONArray();
    }

    public static JSONArray of(Object... objects) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(Arrays.asList(objects));
        return jsonArray;
    }

    public static JSONArray of(Collection<?> objects) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(objects);
        return jsonArray;
    }

    public static <T> Set<T> toSet(String src, Class<T> type) {
        List<T> convert = toList(src, type);
        return new HashSet<>(convert);
    }

    public static <T> List<T> toList(String src, Class<T> type) {
        return JSONArray.parseArray(src, type);
    }

}
