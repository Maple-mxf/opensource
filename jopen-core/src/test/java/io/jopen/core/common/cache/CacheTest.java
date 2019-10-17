package io.jopen.core.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Multimaps;
import org.junit.Test;

import java.time.Duration;

/**
 * @author maxuefeng
 */
public class CacheTest {

    @Test
    public void testGuavaCacheSimpleAPI() {

        Cache<String, Object> cache = CacheBuilder.newBuilder().build();

        cache.put("word", "Hello Guava Cache");

        System.err.println(cache.getIfPresent("word"));
    }

    @Test
    public void setCacheMaxSize() {

        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(2).build();

        cache.put("1", 2);
        cache.put("2", "Kei");
        cache.put("3", "Hei");

        // {3=Hei, 2=Kei}
        System.err.println(cache.asMap());

        System.err.println("9774E4D811CCFCB7775F40C665D4069DC6BBE6A1C6EE86FBD0BE3CF8000BAE3E".length());
    }

    @Test
    public void setCacheExpire() throws InterruptedException {

        Cache<Object, Object> cache = CacheBuilder.newBuilder().expireAfterAccess(Duration.ofSeconds(2)).build();

        cache.put("1", 2);
        cache.put("2", "Kei");
        cache.put("3", "Hei");

        while (true) {
            System.err.println(cache.asMap());
            Thread.sleep(1000);
            // Multimaps.
        }
    }

    // D1D83C1A6758A3B57CF78E4089109FC9553F391B63211B065456B1280967D48F
    // 4F1B4A54A1B8BCE55D4433EAF5BF3E37D556ADD9BE55DCA997348B8658FB3B94
}
