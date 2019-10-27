package io.jopen.snack.common.listener;

import com.google.common.util.concurrent.*;
import io.jopen.snack.common.event.SnackApplicationEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * <p>{@link io.jopen.snack.common.event.SnackApplicationEvent}</p>
 *
 * @author maxuefeng
 * @see com.google.common.util.concurrent.ThreadFactoryBuilder
 * @since 2019/10/27
 */
public abstract class SnackApplicationListener {

    // 执行任务的监听器
    private final ListeningExecutorService service =
            MoreExecutors.listeningDecorator(
                    Executors.newFixedThreadPool(50,
                            new ThreadFactoryBuilder()
                                    .setNameFormat("snack-listener-%d")
                                    .setUncaughtExceptionHandler((thread, e) -> System.err.println(String.format("Thread %s create failure ,cause [ %s ]", thread.getName(), e.getMessage())))
                                    .setPriority(Thread.NORM_PRIORITY)
                                    .build()));


    final void submit(@NonNull Callable task,
                      @NonNull FutureCallback<Boolean> callback) {

        // 提交任务
        ListenableFuture future = this.service.submit(task);
        // 回调函数
        Futures.addCallback(future, callback, service);
    }

    // 把事件对象作为参数
    abstract void handEvent(@NonNull SnackApplicationEvent event);
}
