package io.jopen.ssh;

import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.Comparator;
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
public final class LinuxDevice implements Comparator<Integer> {

    /**
     * 默认SSH端口
     */
    private static final int DEFAULT_SSH_PORT = 22;

    /**
     * 每个Linux服务器最多同时可以执行多少个任务
     */
    private static final int DEFAULT_PARALLEL = 4;

    /**
     * root用户名
     *
     * @see Account#getUsername()
     */
    private static final String ROOT = "root";

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
     * 连接port
     */
    private int port = DEFAULT_SSH_PORT;

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

    /**
     * 当前Linux对应的连接
     */
    private ch.ethz.ssh2.Connection connection;

    /**
     * 当前Linux connection对应的info
     *
     * @see ConnectionInfo
     */
    private ConnectionInfo connectionInfo;

    /**
     * session的连接状态
     *
     * @see Session
     */
    private SessionConnectState sessionConnectState;


//    private String
//    private String rootPassword;

    @Override
    public int compare(Integer p1, Integer p2) {
        if (p1 - p2 == 0) return 0;
        if (p1 - p2 > 0) return 1;
        if (p1 - p2 < 0) return -1;
        throw new RuntimeException();
    }


    enum PlatformSystem {
        UBUNTU16,
        UBUNTU18,
        CENTOS7,
        CENTOS6
    }

    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip,
                       int port) throws IOException {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.parallelism = DEFAULT_PARALLEL;

        ch.ethz.ssh2.Connection connection = new ch.ethz.ssh2.Connection(ip);
        this.connectionInfo = connection.connect();

        this.connection = connection;
        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }


    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip,
                       int port, int parallelism) throws IOException {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.parallelism = parallelism;

        ch.ethz.ssh2.Connection connection = new ch.ethz.ssh2.Connection(ip);
        this.connectionInfo = connection.connect();

        this.connection = connection;
        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }

    public LinuxDevice(@NonNull String alias,
                       @NonNull String ip,
                       int port, int priority, int parallelism) throws IOException {

        Preconditions.checkNotNull(alias);
        Preconditions.checkNotNull(ip);

        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.parallelism = parallelism;
        this.priority = priority;

        ch.ethz.ssh2.Connection connection = new ch.ethz.ssh2.Connection(ip);
        this.connectionInfo = connection.connect();
        this.connection = connection;
        this.executor = getDefaultExecutor();
        this.state = State.NORMAL;
    }


    /**
     * TODO  当前机器的连接状态
     */
    enum SessionConnectState {
        CONNECTED,
        NO_CONNECT,
        BROKEN
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
     * 执行登录任务
     */
    Session login(Account account) throws IOException {


        boolean authenticateSuccess;
        if (account.getLoginType().equals(Account.LoginType.PASSWORD)) {
            authenticateSuccess = connection.authenticateWithPassword(account.getUsername(), account.getPassword());

        } else if (account.getLoginType().equals(Account.LoginType.SECRET)) {
            authenticateSuccess = connection.authenticateWithPublicKey(account.getUsername(), account.getSecret(), account.getPassword());
        } else
            throw new RuntimeException("unsupport login type");

        if (!authenticateSuccess)
            throw new RuntimeException("auth failed,please check your account and password");

        return connection.openSession();
    }


    /**
     * 切换到Root用户
     *
     * @see LinuxDevice#ROOT
     */
    void su() {

    }

    /**
     * @see java.util.concurrent.Future
     * 异步结果通知
     */
    public <T> void submitTask(Task<T> task, Callback<T> callback) {
        ListenableFuture<T> future = executor.submit(task);
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

    /**
     * @param <T>
     * @see LinuxDevice#executeTaskNum
     */
    public abstract class Callback<T> implements FutureCallback<T> {

        @Override
        public void onSuccess(@Nullable T result) {
            LinuxDevice.this.executeTaskNum.getAndDecrement();
            completedOnSuccess(result);
        }

        abstract void completedOnSuccess(@Nullable T result);

        @Override
        public void onFailure(Throwable t) {
            LinuxDevice.this.executeTaskNum.getAndDecrement();

            if (LinuxDevice.this.executeTaskNum.get() < LinuxDevice.this.parallelism) {
                // LinuxDeviceManager.LINUX_DEVICE_MANAGER.addDevice();
            }

            completedOnFailure(t);
        }

        abstract void completedOnFailure(Throwable t);
    }

    public String getAlias() {
        return alias;
    }
}
