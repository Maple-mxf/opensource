package io.jopen.core.common.json;

import org.json.JSONPointer;

import java.io.Serializable;
import java.util.List;

/**
 * JSON指针是RFC 6901为JSON文档定义的简单查询语言。
 * 简而言之，JSONPointer允许用户使用字符串导航到JSON文档，并检索目标对象，如XPATH的简单形式。
 * 路径段由'/'字符分隔，当字符串显示为字符串的第一个字符时，它表示文档的根。
 * 使用序数导航数组元素，从0开始计数.JSONPointer字符串可以扩展到任意数量的段。
 * 如果导航成功，则返回匹配的项目。匹配的项可以是JSONObject，
 * JSONArray或JSON值。如果JSONPointer字符串构建失败，则抛出相应的异常。
 * 如果导航无法找到匹配项，则抛出JSONPointerException。
 * <p>
 * <p>
 * Json 指针快速操作器
 *
 * @author maxuefeng
 * @see org.json.JSONPointer
 */
public class JsonPointer implements Serializable {

    private JsonPointer() {
    }

    public static JSONPointer of(String pointer) {
        return new JSONPointer(pointer);
    }

    public static JSONPointer of(List<String> refTokens) {
        return new JSONPointer(refTokens);
    }
}
