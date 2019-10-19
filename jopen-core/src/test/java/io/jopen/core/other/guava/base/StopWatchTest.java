package io.jopen.core.other.guava.base;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class StopWatchTest {

    @Test
    public void testSimpleAPI() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (int i = 0; i < 1000; i++) {
            System.err.println("循环中");
        }
        System.err.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
