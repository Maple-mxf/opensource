package io.jopen.core.function.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.junit.Test;

/**
 * @author maxuefeng
 * @see com.google.common.base.Predicates
 * @see com.google.common.base.Predicate
 * @see java.util.function.Predicate
 * @since 2019/10/17
 */
public class PredicatesTest {

    @Test
    public void testSimpleAPI() {
        Predicate<Object> alwaysFalse = Predicates.alwaysFalse();
        System.err.println(alwaysFalse.test(100));
    }
}
