package io.jopen.core.common.collection;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Collection;

/**
 * @author maxuefeng
 * @see Collections2
 */
public class Collections2Test {

    @Test
    public void testSimpleAPI() {

        Collection<String> rs = Collections2.filter(ImmutableList.of("1", "2"), "2"::equals);

        System.out.println(rs);
    }
}
