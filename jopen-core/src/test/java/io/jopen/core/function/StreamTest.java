package io.jopen.core.function;

import com.google.common.collect.ImmutableBiMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

/**
 * @author maxuefeng
 */
public class StreamTest {

    @Test
    public void testMatch() {

        ImmutableBiMap<Object, Object> var = ImmutableBiMap.of();

        String[] fields = {"1","2"};

        System.err.println(Arrays.stream(fields).anyMatch(t -> var.get(t) == null));
    }
}
