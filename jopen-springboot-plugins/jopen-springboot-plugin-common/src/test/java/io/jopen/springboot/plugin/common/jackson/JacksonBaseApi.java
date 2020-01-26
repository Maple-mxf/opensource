package io.jopen.springboot.plugin.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
public class JacksonBaseApi {

    @Test
    public void baseApi() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> hashMap = new HashMap<>();
        String jsonString = mapper.writeValueAsString(hashMap);
        System.err.println(jsonString);
    }
}
