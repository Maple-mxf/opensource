package io.jopen.ssh;

import ch.ethz.ssh2.Session;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.*;
import io.jopen.ssh.task.ListeningCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个{@link LinuxDevice}可以保持多个{@link Session}连接
 * 而每一个{@link Session}的状态在{@link LinuxDevice}保持唯一的
 *
 * @author maxuefeng
 * @see LinuxDeviceManager
 * @since 2020/2/11
 */
public final class LinuxDevice implements Comparable<LinuxDevice> {

    /**
     * 每个Linux服务器最多同时可以执行多少个任务
     */
    private static final int DEFAULT_PARALLEL = 4;

    /**
     * 并行度
     */
    private final int parallelism;

    /**
     * 并行执行器
     */
    private final ListeningExecutorService executor;

    /**
     * 当前执行任务的数量
     */
    private AtomicInteger executeTaskNum = new AtomicInteger(0);

    /**
     * 机器别名
     */
    private String alias;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 机器状态
     */
    private State state;

    /**
     * @see java.util.PriorityQueue
     */
    private int priority = 1;

    /**
     * 系统类型
     */
    private PlatformSystem platformSystem;

    @Override
    public int compareTo(LinuxDevice d) {
        return Integer.compare(d.priority - this.priority, 0);
    }

    /**
     * 系统类型
     */
    enum PlatformSystem {
        UBUNTU16,
        UBUNTU18,
        CENTOS7,
        CENTOS6
    }

    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip) {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.parallelism = DEFAULT_PARALLEL;

        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }


    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip,int parallelism) {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.parallelism = parallelism;

        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }

    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip, int priority, int parallelism) {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.parallelism = parallelism;
        this.priority = priority;

        ch.ethz.ssh2.Connection connection = new ch.ethz.ssh2.Connection(ip);
        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }

    enum State {

        /**
         * 正常状态
         */
        NORMAL,

        /**
         * 不可用状态
         */
        BROKEN
    }

    /**
     * @see java.util.concurrent.Future
     * 异步结果通知
     */
    <T> void submitTask(ListeningCallable<T> listeningCallable, FutureCallback<T> callback) {

        if (this.executeTaskNum.get() >= parallelism) {
            throw new RuntimeException(String.format("current device %s parallelism is %s, max submit listeningCallable number is %s", this, parallelism, this.executeTaskNum));
        }

        ListenableFuture<T> future = executor.submit(listeningCallable);
        Futures.addCallback(future, callback, executor);
    }

    private static ListeningExecutorService getDefaultExecutor() {
        BlockingQueue<Runnable> taskCacheQueue = new LinkedBlockingQueue<>(20);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                // 核心线程数量
                DEFAULT_PARALLEL,
                // 最大线程数量
                DEFAULT_PARALLEL,
                // 闲置线程的最大存活时间
                100, TimeUnit.SECONDS,
                // 任务缓存队列
                taskCacheQueue,
                // 线程创建工厂
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("LinuxDevice-%d").build(),
                (runnable, executor) -> {
                    // 如果任务提交失败，阻塞当前主线程
                    executor.submit(runnable);
                });
        return MoreExecutors.listeningDecorator(threadPoolExecutor);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinuxDevice that = (LinuxDevice) o;
        return Objects.equals(alias, that.alias) &&
                Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, ip);
    }

    public String getAlias() {
        return alias;
    }

    public AtomicInteger getExecuteTaskNum() {
        return executeTaskNum;
    }

    public String getIp() {
        return ip;
    }
}
