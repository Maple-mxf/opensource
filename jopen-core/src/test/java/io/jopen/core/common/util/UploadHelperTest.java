package io.jopen.core.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import io.jopen.core.common.net.UploadHelper;
import org.junit.Test;

import java.io.File;

/**
 * @author maxuefeng
 */
public class UploadHelperTest {

    @Test
    public void testSimpleUploadFile() {

        JSONObject result = UploadHelper.upload("http://localhost:8080/planet/api/consumption/credential/upload",
                new File("E:\\workplace\\jopen-core\\tmp\\1.jpg"),
                ImmutableMap.of("Planet-Access-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1SWQiOiIwZGE2YTBkZDMxMTU0ZjBkOGI3NDIwYjg1MzNhNDgwZCIsImV4cCI6MTU1NjQ1MjAyOH0.4Z8-Fzsu_hP9lgwMvu9ix3zwb5wNnhrAkgnEfBeODmw"));

        System.err.println(result);
    }
}
