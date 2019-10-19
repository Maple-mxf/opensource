package io.jopen.core.other.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.jopen.core.common.text.StringHelper;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaCacheTest {

    public void testSimpleAPI() throws ExecutionException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(1000)
                // 设置写之后的过期时间
                .expireAfterWrite(1000, TimeUnit.SECONDS)
                // 初始化容量
                .initialCapacity(1000)
                .build();


        // 如果本地缓存中不存在testKey对应的value  则使用callable将目标对象存入本地缓存
        cache.get("testKey", () -> StringHelper.randomString(100));

    }
}
