package io.jopen.core.common.collection;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author maxuefeng
 * @see Future  预期  也就是线程执行的结果
 * @since 2019/10/13
 */
public class ImmuteCollectionTest {


    /**
     * 好处 ： 只能被初始化一次  线程安全 不可全
     * 坏处 ： 不可变
     */
    @Test
    public void testList() {
        ImmutableList<String> list = ImmutableList.of("HelloWorld");


        System.err.println("测试不可变集合");

        list.add("Hello Jack");


        ArrayList<Object> objects = Lists.newArrayList();

    }

    // cache  cache   1  性能  2 线程安全   3  过期策略

    Cache<Object, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)  // size
            .expireAfterWrite(10, TimeUnit.MINUTES)  // 过期策略
            .build(); // 构建

    //
    public void pullCache() {
        // 一个进程   就是一个Java程序
        cache.put("", "");
    }


    public void pushCache() throws ExecutionException {
        cache.get("", new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                // 引发的callback钩子函数


                return null;
            }
        });
    }

    static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListenableFuture<?> future = service.submit((Callable<Object>) () -> "HelloWorld");

        // block  thread   同步
        Object o = future.get();

        future.addListener(() -> System.err.println("我是处理future结果的"), service);


        Futures.addCallback(future, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {


            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, service);


        System.err.println(o);


        HashBasedTable<Object, Object, Object> table = HashBasedTable.create();

        table.put("rowKey","columnKey","value");
    }


    // Jedis  Redisson  Tedis
}
