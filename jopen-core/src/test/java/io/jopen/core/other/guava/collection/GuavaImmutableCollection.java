package io.jopen.core.other.guava.collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaImmutableCollection {

    @Test
    public void testSimpleAPI() {
        // 不可变List
        ImmutableList<String> immutableList = ImmutableList.of("one", "two");

        // 不可变Set
        ImmutableSet<String> immutableSet = ImmutableSet.of("one", "two");
    }

    @Test
    public void multiMap(){
        ArrayListMultimap<String, Integer> map = ArrayListMultimap.create();
        map.put("key",1);
        map.put("key",2);
        // result [1,2]
        System.err.println(map.get("key"));
    }

}
