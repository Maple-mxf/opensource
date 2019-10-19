package io.jopen.core.other.guava.collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaSetTest {

    @Test
    public void testSetUnionAndDifferenceAndIntersection(){
        ImmutableSet<String> set1 = ImmutableSet.of("H", "B","A");
        ImmutableSet<String> set2 = ImmutableSet.of("A", "B");

        // 并集
        Sets.SetView<String> unionSet = Sets.union(set1, set2);
        // result:HBA
        unionSet.forEach(System.err::print);
        System.err.println();

        // 差集
        Sets.SetView<String> differenceSet = Sets.difference(set1, set2);
        // result:H
        differenceSet.forEach(System.err::print);
        System.err.println();

        // 并集
        Sets.SetView<String> intersectionSet = Sets.intersection(set1, set2);
        // result:BA
        intersectionSet.forEach(System.err::print);
    }
}
