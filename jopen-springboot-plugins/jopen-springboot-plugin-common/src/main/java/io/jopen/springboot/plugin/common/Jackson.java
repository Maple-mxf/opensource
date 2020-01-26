package io.jopen.springboot.plugin.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author maxuefeng
 * @since 2020/1/26
 */
public final
class Jackson {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJson(@NonNull Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String keyValueString(@NonNull KV kv) {
        Map<Object, Object> objectMap = KV.of();
        try {
            return MAPPER.writeValueAsString(objectMap);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
