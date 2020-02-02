package io.jopen.zookeeper.recipes.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     Fully distributed locks that are globally synchronous, meaning at any snapshot in time no two clients
 *     think they hold the same lock. Note: unlike InterProcessMutex this lock is not reentrant.
 *     {@link InterProcessSemaphoreMutex}
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/2
 */
public class RecipesSharedLock {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final InterProcessSemaphoreMutex interProcessSemaphoreMutex;

    private final String lockPath = "/interSemaphoreMutexNode";

    public RecipesSharedLock() {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();

        interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorClient, lockPath);
    }

    public void lock(@NonNull Runnable runnable) throws Exception {
        interProcessSemaphoreMutex.acquire();
        runnable.run();
        interProcessSemaphoreMutex.release();
    }

    /**
     * @param runnable
     * @param awaitTimeMs
     * @param cycleGet
     * @throws Exception
     * @see InterProcessSemaphoreMutex#isAcquiredInThisProcess()
     */
    public void lock(@NonNull Runnable runnable, long awaitTimeMs, boolean cycleGet) throws Exception {
        if (cycleGet) {
            boolean acquire = interProcessSemaphoreMutex.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            while (!acquire) {
                acquire = interProcessSemaphoreMutex.acquire(awaitTimeMs, TimeUnit.MICROSECONDS);
            }
            runnable.run();
            interProcessSemaphoreMutex.release();
        } else {
            if (interProcessSemaphoreMutex.acquire(awaitTimeMs, TimeUnit.MILLISECONDS)) {
                runnable.run();
                interProcessSemaphoreMutex.release();
            }
        }
    }
}
