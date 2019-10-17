package io.jopen.core.common.json.xpath;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @see com.alibaba.fastjson.JSONPath
 * @since 2019/10/17
 */
public class FastJsonPathOperateJSONTest {
    String jsonStr = "{ \"store\": {\"book\": [{ \"category\": \"reference\"," +
            "\"author\": \"Nigel Rees\",\"title\": \"Sayings of the Century\"," +
            "\"price\": 8.95},{ \"category\": \"fiction\",\"author\": \"Evelyn Waugh\"," +
            "\"title\": \"Sword of Honour\",\"price\": 12.99,\"isbn\": \"0-553-21311-3\"" +
            "}],\"bicycle\": {\"color\": \"red\",\"price\": 19.95}}}";

    JSONObject target = null;

    @Before
    public void before() {
        target = JSON.parseObject(jsonStr);
    }

    @Test
    public void testGetEleByXPath() {
        // 查找特定元素
        Object result1 = JSONPath.eval(target, "$.store.book");
        System.err.println(String.format("[result1]指定元素是：%s", String.valueOf(result1)));

        // 调用指定元素的成员方法
        Object result2 = JSONPath.eval(target, "$.store.book.size()");
        System.err.println(String.format("[result2]指定元素的size %s", String.valueOf(result2)));

        // 进行条件匹配指定的元素
        Object result3 = JSONPath.eval(target, "$.store.book[price > 10]");
        System.err.println(String.format("[result3]价格大于10的书有 %s", String.valueOf(result3)));

        // 获取指定元素的属性
        Object result4 = JSONPath.eval(target, "$.store.book[price > 10][0].price");
        System.err.println(String.format("[result4]价格大于10的书的第一本书的价格是 %s", String.valueOf(result4)));

        // 获取指定元素的所有属性
        Object result5 = JSONPath.eval(target, "$.store.bicycle.*");
        System.err.println(String.format("[result5]获取指定元素的所有属性为 %s", String.valueOf(result5)));

        // 获取指定元素的指定属性
        Object result6 = JSONPath.eval(target, "$.store.bicycle['color','price']");
        System.err.println(String.format("[result6]获取指定元素的指定属性为 %s", String.valueOf(result6)));
    }
}
