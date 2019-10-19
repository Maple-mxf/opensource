package io.jopen.core.other.guava.collection;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Test;

import java.util.Iterator;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaIterablesTest {

    @Test
    public void testFilterCollectionEle(){
        ImmutableList<String> list = ImmutableList.of("Hello", "World");
        Iterator<String> iterator = Iterables.filter(list, input -> !Strings.isNullOrEmpty(input) && input.contains("H")).iterator();
        // result:Hello
        iterator.forEachRemaining(System.err::println);
    }
}
