package io.jopen.zookeeper.recipes.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.RevocationListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <h4>
 * Zookeeper Curator recipes Distribute lock base on ReentrantLock(可重入性质的锁)
 * </h4>
 *
 * <pre>
 *     Fully distributed locks that are globally synchronous,
 *     meaning at any snapshot in time no two clients think they hold the same lock.
 * </pre>
 * <pre>
 *         在分布式环境下，分布式锁是全局性质的定义，也就是以为这同一时刻，多个客户端都会竞争通一把锁的资源
 *         也称为临界区(临界区中包含的是临界资源)
 *     </pre>
 * <p>
 * 基于zookeeper的recipes模块的分布式可重入锁的实现；跟java的关键字synchronized的机制类似，也可参考、java
 * 的Lock机制{@link java.util.concurrent.locks.ReentrantLock}
 * <p>
 * <p>
 * <p>
 * {@link org.apache.curator.framework.recipes.locks.InterProcessMutex}
 *
 * @author maxuefeng
 * @see InterProcessMutex#makeRevocable(RevocationListener)
 * @since 2020/2/1
 */
public class RecipesSharedReentrantLock {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final InterProcessMutex interProcessMutex;

    private final String lockPath = "/interMutexNode";

    public RecipesSharedReentrantLock() {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
        // create interProcess instance
        interProcessMutex = new InterProcessMutex(curatorClient, lockPath);
    }

    /**
     * @param runnable 开发者自定义的临界区的代码
     * @see InterProcessMutex#acquire()
     * @see InterProcessMutex#acquire(long, TimeUnit)
     */
    public void lock(@NonNull Runnable runnable) throws Exception {
        // still block current tread until get the lock(直到获取到临界资源当前线程才会进入Runnable状态 否则一直处于Block)
        interProcessMutex.acquire();
        runnable.run();
        interProcessMutex.release();
    }

    /**
     * Acquire the mutex - blocks until it's available or the given time expires. Note: the same thread
     * can call acquire re-entrantly. Each call to acquire that returns true must be balanced by a call
     * <p>
     * 会一直等待其他线程释放锁，如果在指定时间获取不到锁，则会返回为false
     * 不建议使用循环获取
     *
     * @param runnable    开发者自定义的临界区的代码
     * @param awaitTimeMs 等待时长
     * @param cycleGet    是否循环获取锁资源
     * @throws Exception ZK errors, connection interruptions
     */
    public void lock(@NonNull Runnable runnable, long awaitTimeMs, boolean cycleGet) throws Exception {
        if (cycleGet) {
            boolean acquire = interProcessMutex.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            while (!acquire) {
                acquire = interProcessMutex.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            }
            runnable.run();
            interProcessMutex.release();
        } else {
            if (interProcessMutex.acquire(awaitTimeMs, TimeUnit.MILLISECONDS)) {
                runnable.run();
                interProcessMutex.release();
            }
        }
    }

    /**
     * 撤销分布式锁
     *
     * @see InterProcessMutex#makeRevocable(RevocationListener)
     * @see org.apache.curator.framework.recipes.locks.Revoker#attemptRevoke(CuratorFramework, String) 尝试撤销
     */
    public void makeRevocation() {
        this.interProcessMutex.makeRevocable(new RevocationListener<InterProcessMutex>() {
            public void revocationRequested(InterProcessMutex forLock) {
                try {
                    forLock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, Executors.newSingleThreadExecutor());
    }
}
