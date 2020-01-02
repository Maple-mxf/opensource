package io.jopen.util.concurrent;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * {@link com.google.common.util.concurrent.Service}
 * {@link java.util.concurrent.ThreadPoolExecutor}
 * {@link LinkedBlockingQueue#offer(Object)}
 * {@link LinkedBlockingQueue} LinkedBlockQueue默认的大小是Integer.MAX_VALUE  容易出现内存溢出
 * <p>
 * {@link CompletableFuture}
 *
 * @author maxuefeng
 * @since 2019/12/4
 */
public class ThreadPoolExecutorTest {


    /**
     * 任务缓存队列
     * {@link LinkedBlockingQueue#LinkedBlockingQueue(int)}
     */
    private BlockingQueue<Runnable> taskCacheQueue = new LinkedBlockingQueue<>(20);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            10,
            // 最大线程数量
            15,
            // 闲置线程的最大存活时间
            100, TimeUnit.SECONDS,
            // 任务缓存队列
            taskCacheQueue,
            // 线程创建工厂
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("es-data-%d").build(),
            (runnable, executor) -> {
                // 如果任务提交失败，阻塞当前主线程
                executor.submit(runnable);
            }
    );

    /**
     * @see com.google.common.util.concurrent.Futures
     * @see ThreadPoolExecutor#submit(java.util.concurrent.Callable)
     */
    @Test
    public void testSubmitTask() {
        Future<Void> future = this.threadPoolExecutor.submit(new Task());
    }

    class Task implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            Thread.sleep(10000);
            System.err.println("任务执行完成");

            return null;

        }
    }


    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);


    @Test
    public void testGuavaExecutorSubmitTask() {
        // 任务提交
        ListenableFuture<Void> listenableFuture = listeningExecutorService.submit(new Task());

        Futures.addCallback(listenableFuture, new FutureCallback<Void>() {
            @Override
            public void onSuccess(@Nullable Void result) {
                System.err.println(String.format("任务执行结果 ： {%s} ", result));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }, this.threadPoolExecutor);
    }


    /**
     * @see CompletableFuture#acceptEitherAsync(CompletionStage, Consumer)
     * @see CompletionStage#acceptEitherAsync(CompletionStage, Consumer)
     * @see CompletableFuture#supplyAsync(Supplier, Executor)  supplier方式
     * @see CompletableFuture#runAsync(Runnable, Executor)   consumer方式
     */
    @Test
    public void testCompleteFutureBaseApi() {

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return String.valueOf(new Task().call());
            } catch (Exception e) {
                e.printStackTrace();
                return "执行任务发生异常";
            }

        }, this.threadPoolExecutor)
                .whenComplete((result, throwable) -> {
                    System.err.println(String.format("执行任务过程中是否发生异常 {%s} ", throwable == null));
                    System.err.println(String.format("异步计算结果 %s", result));
                });
    }


}
