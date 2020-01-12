package io.jopen.snack.common;

import io.jopen.snack.common.serialize.KryoHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * {@link IntermediateExpression}
 * eq Map<>
 *
 * @author maxuefeng
 * @since 2019/10/26
 */
public class LambdaSerialization {

    private Condition condition = (Condition) Objects::nonNull;

    @Test
    public void testSerialization() throws IOException {
        byte[] data = KryoHelper.serialization(condition);
        Condition condition1 = KryoHelper.deserialization(data, Condition.class);
        boolean test = condition1.test("2");
        System.err.println(test);
    }
}
