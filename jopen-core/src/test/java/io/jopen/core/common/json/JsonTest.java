package io.jopen.core.common.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author maxuefeng
 */
public class JsonTest {


    /**
     * 获取一个JSONObject的最外层的Key
     */
    @Test
    public void testGetKeys() {

        String jsonString = "{\"code\":{\"k\":\"v\"}}";
        JSONObject var = Json.of(jsonString);
        var.keySet().forEach(System.out::println);

    }

    @Test
    public void testSimpleAPI() {
        JSONObject o = Json.of("k1", "v1");
        System.err.println(o);
    }

    static class Student {
        LocalDateTime insertTime;

        public Student(LocalDateTime insertTime) {
            this.insertTime = insertTime;
        }
    }

    @Test
    public void testJSONConvertJavaObject() {
       /* // BUG
        JSONObject jsonObject = new JSONObject(new Student(LocalDateTime.now()));
        System.err.println(jsonObject);*/
    }

    @Test
    public void testJSONArrayToJavaList(){
        String source = "{\"ossKey\":\"583889989516001280\",\"data\":[\"饺子店\",\"5天前消费\"],\"accessUrl\":\"http://biz-qmbx.oss-cn-beijing.aliyuncs.com/583889989516001280\",\"ossBucket\":\"biz-qmbx\",\"formatName\":\"jpg\",\"OCRMode\":\"Tess\",\"ocrDataMode\":\"Single_text\",\"type\":1,\"insertTime\":1559280623044,\"uid\":\"582505345674469376\",\"identification\":-46766217,\"size\":68.1240234375,\"originCreateTime\":1559280620000,\"device\":\"ios\",\"originName\":\"1.jpg\"}";

        JSONObject jsonObject = JSONObject.parseObject(source);

        JSONArray jsonArray = jsonObject.getJSONArray("data");

        List<String> list = jsonArray.toJavaList(String.class);

        System.err.println(list);
    }
}
