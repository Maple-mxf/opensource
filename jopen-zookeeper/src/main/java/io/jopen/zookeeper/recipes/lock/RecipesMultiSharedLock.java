package io.jopen.zookeeper.recipes.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     A container that manages multiple locks as a single entity. When acquire() is called,
 *     all the locks are acquired. If that fails, any paths that were acquired are released.
 *     Similarly, when release() is called, all locks are released (failures are ignored).
 *
 *     {@link InterProcessMultiLock}
 *     {@link InterProcessLock}
 * </pre>
 *
 *
 * <pre>
 *     将多个锁作为单个实体管理的容器。调用acquire（）时，
 *     所有的锁都得到了。如果失败，将释放获取的任何路径。
 *    类似地，当调用release（）时，将释放所有锁（忽略失败）。
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/2
 */
public final class RecipesMultiSharedLock {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final InterProcessMultiLock interProcessMultiLock;

    /**
     * {@link InterProcessMultiLock#InterProcessMultiLock(List)}
     *
     * @see InterProcessLock 基于此接口
     */
    public RecipesMultiSharedLock(@NonNull List<InterProcessLock> interProcessLocks) {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
        interProcessMultiLock = new InterProcessMultiLock(interProcessLocks);
    }

    public void lock(Runnable runnable) throws Exception {
        this.interProcessMultiLock.acquire();
        runnable.run();
        this.interProcessMultiLock.release();
    }

    public void lock(@NonNull Runnable runnable, long awaitTimeMs, boolean cycleGet) throws Exception {
        if (cycleGet) {
            boolean acquire = interProcessMultiLock.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            while (!acquire) {
                acquire = interProcessMultiLock.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            }
            runnable.run();
            interProcessMultiLock.release();
        } else {
            if (interProcessMultiLock.acquire(awaitTimeMs, TimeUnit.MILLISECONDS)) {
                runnable.run();
                interProcessMultiLock.release();
            }
        }
    }
}
