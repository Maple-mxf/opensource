package io.jopen.zookeeper.recipes.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     A re-entrant read/write mutex that works across JVMs. Uses Zookeeper to hold the lock.
 *     All processes in all JVMs that use the same lock path will achieve an inter-process critical section.
 *     Further, this mutex is "fair" each user will get the mutex in the order requested (from ZK's point of view).
 *     A read write lock maintains a pair of associated locks, one for read-only operations and one for writing.
 *     The read lock may be held simultaneously by multiple reader processes, so long as there are no writers.
 *     The write lock is exclusive.
 *     Reentrancy This lock allows both readers and writers to reacquire read or write locks in the style of
 *     a re-entrant lock. Non-re-entrant readers are not allowed until all write locks held by the writing
 *     thread/process have been released. Additionally, a writer can acquire the read lock, but not vice-versa.
 *     If a reader tries to acquire the write lock it will never succeed.
 *     Lock Downgrading Re-entrancy also allows downgrading from the write lock to a read lock, by acquiring
 *     the write lock, then the read lock and then releasing the write lock. However, upgrading from a read lock
 *     to the write lock is not possible.
 *     {@link InterProcessReadWriteLock}
 *     {@link InterProcessLock}
 * </pre>
 *
 * <pre>
 *     读写锁无法升级（读锁无法升级到写锁）
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/2
 */
public final class RecipesSharedReentrantReadWriteLock {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final InterProcessReadWriteLock interProcessReadWriteLock;


    private final String lockPath = "/localRootPath";

    public RecipesSharedReentrantReadWriteLock() {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();

        interProcessReadWriteLock = new InterProcessReadWriteLock(curatorClient, lockPath);
    }

    /**
     * Access either the read lock or the write lock and then use the methods as described for Shared lock.
     */
    public void readLock(@NonNull Runnable runnable) throws Exception {
        InterProcessMutex interProcessMutex = this.interProcessReadWriteLock.readLock();
        interProcessMutex.acquire();

        runnable.run();

        interProcessMutex.release();
    }

    /**
     * @param runnable    开发者自定义的临界区的代码
     * @param awaitTimeMs 等待时长
     * @param cycleGet    是否循环获取锁资源
     * @throws Exception ZK errors, connection interruptions
     */
    public void writeLock(@NonNull Runnable runnable, long awaitTimeMs, boolean cycleGet) throws Exception {
        InterProcessMutex interProcessMutex = this.interProcessReadWriteLock.writeLock();
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
}
