package io.jopen.springboot.plugin.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.jopen.springboot.plugin.common.Formatter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author maxuefeng
 */
public class PatternLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    /**
     * @param date
     * @param generator
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(Formatter.format(date, Formatter.P.P2_0));
    }
}
