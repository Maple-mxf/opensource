package io.jopen.core.function.util;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import org.junit.Test;

import java.util.Set;

/**
 * @author maxuefeng
 * @see com.google.common.base.Optional
 * @see java.util.Optional
 * @since 2019/10/17
 */
public class OptionalTest {
    @Test
    public void testSimpleAPI() {
        Optional<String> optional = Optional.of("");

        Set<String> strings = optional.asSet();

        System.err.println(strings);

    }
}
