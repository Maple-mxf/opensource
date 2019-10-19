package io.jopen.core.other.guava.concurent;

import com.google.common.util.concurrent.*;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @author maxuefeng
 * @since 2019/10/19
 */
public class GuavaExecutorTest {

    // 使用装饰器模式创建线程池防止OOM错误
    private ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1000));

    @Test
    public void testSimpleAPI() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> future = listeningExecutorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return RandomUtils.nextInt();
            }
        });

        // 阻塞线程  获取远期结果
        Integer futureResult = future.get();

        // 为当前任务执行添加回调函数
        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.err.println("任务完成");
            }
            @Override
            public void onFailure(Throwable t) {
                System.err.println("任务执行失败");
            }
        }, listeningExecutorService);

        // 为当前任务执行添加监听器
        future.addListener(() -> System.err.println("任务执行完成"),listeningExecutorService);
    }
}
