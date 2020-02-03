package io.jopen.zookeeper.recipes.counters;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicStats;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.framework.recipes.atomic.PromotedToLock;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * <h4>Distributed Atomic Long</h4>
 * <pre>
 *     A counter that attempts atomic increments. It first tries using optimistic locking.
 *     If that fails, an optional InterProcessMutex is taken. For both optimistic and mutex,
 *     a retry policy is used to retry the increment
 *     {@link DistributedAtomicLong}
 *     {@link org.apache.curator.framework.recipes.atomic.AtomicValue}
 *     {@link PromotedToLock}
 * </pre>
 *
 * <pre>
 *    尝试原子增量的计数器。它首先尝试使用乐观锁定。如果失败，则采用可选的进程间互斥。
 *    对于optimistic(乐观锁)和mutex，都使用重试策略来重试增量(类似CAS)
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/3
 */
public final class RecipesDistributedAtomicLong {

    private final String connectionString = "192.168.74.136:2181";
    // int baseSleepTimeMs, int maxRetries, int maxSleepM
    // 单位：毫秒
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20, 2000);

    private final CuratorFramework curatorClient;

    private final DistributedAtomicLong distributedAtomicLong;

    private final String counterPath = "/counterPath";
    private final String mutexPath = "/mutexPath";


    /**
     * @param increaseRetryPolicy
     * @see PromotedToLock
     * @see DistributedAtomicLong#decrement()
     * @see DistributedAtomicLong#increment()
     * @see DistributedAtomicLong#subtract(Long)
     * <p>
     * 检查结果原子值：
     * 必须首先检查succeeded（），如果操作成功，则返回true。如果返回false，则操作失败，原子没有更新。
     * 如果操作成功，可以通过preValue（）获取操作之前的值，通过postValue（）获取操作之后的值
     */
    public RecipesDistributedAtomicLong(RetryPolicy increaseRetryPolicy) {
        this.curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        // start the client
        this.curatorClient.start();
        PromotedToLock promotedToLock = PromotedToLock.builder()
                .lockPath(mutexPath)
                .timeout(1000, TimeUnit.MICROSECONDS)
                .build();
        distributedAtomicLong = new DistributedAtomicLong(curatorClient, counterPath, increaseRetryPolicy, promotedToLock);
    }


    /**
     * @throws Exception
     * @see AtomicValue
     */
    public void test() throws Exception {
        AtomicValue<Long> atomicValue = this.distributedAtomicLong.increment();
        // 返回有关操作的调试状态
        AtomicStats stats = atomicValue.getStats();

        // 修改后的值
        Long postValue = atomicValue.postValue();
        // 修改前的值
        Long preValue = atomicValue.preValue();

    }
}
