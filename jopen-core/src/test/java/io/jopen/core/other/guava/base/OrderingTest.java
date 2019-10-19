package io.jopen.core.other.guava.base;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/10/19
 * @see com.google.common.collect.Ordering
 */
public class OrderingTest {

    class Ele{
        public Integer priority;
        public Ele(Integer priority){
            this.priority = priority;
        }
    }

    @Test
    public void testOrderSomeEle(){
        Ordering<Ele> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Ele, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Ele input) {
                return input.priority.toString();
            }
        });

        Ele ele1 = new Ele(1);
        Ele ele2 = new Ele(2);
        int compareRes = ordering.compare(ele1, ele2);
        // result:-1
        System.err.println(compareRes);
    }
}
