package io.jopen.snack.common.listener;

import com.google.common.util.concurrent.*;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.storage.DBManagement;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.*;

/**
 * <p>{@link io.jopen.snack.common.event.SnackApplicationEvent}</p>
 *
 * @author maxuefeng
 * @see com.google.common.util.concurrent.ThreadFactoryBuilder
 * @see java.lang.Thread.UncaughtExceptionHandler
 * @see org.apache.commons.lang3.ThreadUtils
 * @see org.apache.commons.lang3.concurrent.BasicThreadFactory
 * @see ExecutorService
 * {@code}
 * @since 2019/10/27
 */
public abstract class SnackApplicationListener {

    private final BlockingQueue<Runnable> threadBlockingQueue = new LinkedBlockingQueue<>();

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("snack-listener-%d")
            .setUncaughtExceptionHandler((thread, e) -> System.err.println(String.format("Thread %s create failure ,cause [ %s ]", thread.getName(), e.getMessage())))
            .setPriority(Thread.NORM_PRIORITY)
            .build();

    /**
     * @see ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue, ThreadFactory, RejectedExecutionHandler)
     * @see Callable
     * @see Executors
     * @see BasicThreadFactory
     * @see ThreadFactoryBuilder
     * @see RejectedExecutionException
     */
    private final ExecutorService javaService = new ThreadPoolExecutor(
            // 线程核心数量
            50,
            // 最大数量
            100,
            // 执行完任务存活的最大时间
            20000L,
            // 时间单位
            TimeUnit.MICROSECONDS,
            // 线程队列
            threadBlockingQueue,
            // 线程构建工厂
            threadFactory,
            // 线程池任务拒绝策略  有两种情况会出现这种情况 （1 线程池的数量不够了  2 阻塞队列的容量不够了）
            new ThreadPoolExecutor.AbortPolicy()
    );

    // 执行任务的监听器
    private final ListeningExecutorService guavaDecoratorService = MoreExecutors.listeningDecorator(javaService);

    private final BlockingQueue<Callable<Object>> taskQueue = new LinkedBlockingQueue<>();

    protected final DBManagement dbManagement = DBManagement.DBA;

    @Deprecated
    protected final void submit(@NonNull Callable task,
                                @NonNull FutureCallback<Boolean> callback) {

        // 提交任务
        ListenableFuture future = this.guavaDecoratorService.submit(task);
        
        // 回调函数
        Futures.addCallback(future, callback, guavaDecoratorService);
    }

    // 把事件对象作为参数
    public abstract void handEvent(@NonNull SnackApplicationEvent event);

    /**
     * 启动任务执行
     * <p>{@link RejectedExecutionException}</p>
     */
    public void start() {
        new Thread(() -> {
            while (true) {
                try {

                    Callable<Object> callable = SnackApplicationListener.this.taskQueue.take();
                    // 提交任务会抛出异常  线程池的拒绝策略
                    this.guavaDecoratorService.submit(callable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }
}
