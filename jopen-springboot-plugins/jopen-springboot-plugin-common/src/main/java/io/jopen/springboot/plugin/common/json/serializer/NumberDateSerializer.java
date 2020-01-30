package io.jopen.springboot.plugin.common.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @author maxuefeng
 * @see java.text.NumberFormat
 */
public class NumberDateSerializer extends JsonSerializer<Date> {

    /**
     * @param date
     * @param generator
     * @param provider
     * @throws IOException
     */
    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(date.getTime());
    }
}
