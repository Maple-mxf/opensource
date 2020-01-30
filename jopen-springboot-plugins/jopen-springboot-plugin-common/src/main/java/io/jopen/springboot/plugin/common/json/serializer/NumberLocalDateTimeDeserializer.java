package io.jopen.springboot.plugin.common.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.jopen.springboot.plugin.common.LocalDateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 默认序列化为long和反序列化为long
 *
 * @author maxuefeng
 */
public class NumberLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return LocalDateTimeUtil.toLocalDateTime(p.getLongValue());
    }
}
