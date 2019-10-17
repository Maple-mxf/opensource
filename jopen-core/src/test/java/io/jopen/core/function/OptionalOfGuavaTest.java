package io.jopen.core.function;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import org.junit.Test;

/**
 * @author maxuefeng
 * @see Optional
 */
public class OptionalOfGuavaTest {


    /**
     *
     */
    @Test
    public void testSimpleAPI() {

        Optional<Object> op = Optional.absent();

        Object var = op.or("hello");
        System.err.println(var);

        Optional<Object> op2 = Optional.fromNullable(null);
        System.err.println(op2.isPresent());

        String s = MoreObjects.toStringHelper(OptionalOfGuavaTest.class.getName()).addValue(1L).toString();

        System.err.println(s);

    }
}
