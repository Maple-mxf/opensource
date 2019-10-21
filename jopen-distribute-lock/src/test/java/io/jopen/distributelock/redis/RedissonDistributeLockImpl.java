package io.jopen.distributelock.redis;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.RedissonFairLock;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * <p>{@link java.util.concurrent.locks.Lock}</p>
 * <p>{@link RedissonLock#tryLock()}  如果不在tryLock中加入时间参数限制的话，则线程一直会等待锁，知道获取到分布式锁</p>
 * <p>{@link RedissonLock#lock()} 如果不在lock中加入时间参数限制的话，则线程一直会等待锁，知道获取到分布式锁</p>
 * 当然以上两种情况都是基于redisson的内置看门狗的机制下。
 * 如果负责储存这个分布式锁的Redisson节点宕机以后，而且这个锁正好处于锁住的状态时，
 * 这个锁会出现锁死的状态。为了避免这种情况的发生，Redisson内部提供了一个监控锁的看
 * 门狗，它的作用是在Redisson实例被关闭前，不断的延长锁的有效期。默认情况下，看门狗
 * 的检查锁的超时时间是30秒钟，也可以通过修改Config.lockWatchdogTimeout来另行指定。
 * <p>
 * <p>
 * <p>
 * 闭锁
 * <p>
 * 基于Redisson的Redisson分布式闭锁（CountDownLatch）Java对象RCountDownLatch采用了与java.util.concurrent.CountDownLatch相似的接口和用法。
 * <p>{@link java.util.concurrent.CountDownLatch}</p>
 * <p>{@link org.redisson.api.RCountDownLatch}</p>
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public class RedissonDistributeLockImpl {

    private RedissonClient client = null;

    private final String lockName = "distributeLock";

    @Before
    public void before() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.15.41.150:6379").setPassword("12323213");
        client = Redisson.create(config);
    }

    @Test
    public void genericDistributeLock1() {
        RLock lock = client.getLock(lockName);
        // 如果锁不可用，则当前线程变为,出于线程调度目的而禁用，并且处于休眠状态，直到获取锁。
        lock.lock();

        // 执行数据修改操作或者说是更新操作

        // 释放锁
        lock.unlock();
    }

    @Test
    public void genericDistributeLock2() {
        RLock lock = client.getLock(lockName);
        // 如果锁不可用，则当前线程变为,出于线程调度目的而禁用，并且处于休眠状态，直到获取锁。
        boolean isGetLock = lock.tryLock();

        // 获取到了锁
        if (isGetLock)

            // 执行数据修改操作或者说是更新操作

            // 释放锁
            lock.unlock();
    }

    /**
     * 公平锁 意思是指会按照线程的请求的先来后到进行分配锁的拥有者
     *
     * @see RedissonClient#getFairLock(String)
     * @see RedissonFairLock#lock()
     * @see RedissonFairLock#tryLock()
     */
    @Test
    public void fairLock1() {
        // 获取锁对象
        RLock fairLock = client.getFairLock(lockName);

        // 加锁
        fairLock.lock();

        // 执行数据修改操作或者说是更新操作

        // 释放锁
        fairLock.unlock();
    }


}
