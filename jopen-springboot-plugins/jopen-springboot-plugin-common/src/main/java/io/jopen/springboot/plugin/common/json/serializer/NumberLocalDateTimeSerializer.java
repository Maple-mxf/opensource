package io.jopen.springboot.plugin.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author maxuefeng
 */
public class NumberLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    /**
     * @param date
     * @param generator
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(date.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }
}
