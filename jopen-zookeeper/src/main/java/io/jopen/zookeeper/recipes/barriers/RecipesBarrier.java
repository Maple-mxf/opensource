package io.jopen.zookeeper.recipes.barriers;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * <h4>Barrier</h4>
 *
 * <pre>
 *     An implementation of the Distributed Barrier ZK recipe.
 *     Distributed systems use barriers to block processing of a set of nodes until a condition is met
 *     at which time all the nodes are allowed to proceed.
 *
 *     {@link java.util.concurrent.CyclicBarrier}
 *     {@link DistributedBarrier}
 * </pre>
 *
 * <pre>
 *     分布式Barrier ZK配方的实现。
 *     分布式系统使用屏障来阻止一组节点的处理，直到满足允许所有节点继续的条件
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/2
 */
public final class RecipesBarrier {

    private final String connectionString = "192.168.74.136:2181";

    private final CuratorFramework curatorClient;

    // int baseSleepTimeMs, int maxRetries
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20);

    private final DistributedBarrier distributedBarrier;

    private final String lockPath = "lockRootPath";

    /**
     * @see DistributedBarrier#setBarrier()
     */
    public RecipesBarrier(@NonNull List<InterProcessLock> interProcessLocks) {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
        distributedBarrier = new DistributedBarrier(curatorClient, lockPath);
    }



}
