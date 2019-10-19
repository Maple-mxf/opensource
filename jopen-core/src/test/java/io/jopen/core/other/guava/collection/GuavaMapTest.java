package io.jopen.core.other.guava.collection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaMapTest {

    @Test
    public void testMapUnionAndDifferenceAndIntersection() {

        ImmutableMap<String, String> map1 = ImmutableMap.of("k1", "v1", "k2", "v2");
        ImmutableMap<String, String> map2 = ImmutableMap.of("k2", "v2", "k3", "v3");

        MapDifference<String, String> differenceMap = Maps.difference(map1, map2);

        System.err.println(differenceMap.areEqual());
        System.err.println(differenceMap.entriesDiffering());
        System.err.println(differenceMap.entriesInCommon());
        System.err.println(differenceMap.entriesOnlyOnLeft());
        System.err.println(differenceMap.entriesOnlyOnRight());
    }
}
