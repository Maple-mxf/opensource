package io.jopen.distributelock;

import org.redisson.api.RedissonClient;

/**
 * @author maxuefeng
 * @see Runnable
 * @since 2019/10/21
 * <p>{@link DistributeLock}</p>
 */
class RedissonDistributeLock implements DistributeLock {

    private RedissonClient client;

    public Boolean execute(Runnable runnable) {
        lock();
        runnable.run();
        unlock();
        return true;
    }

    @Override
    public void lock() {

    }

    @Override
    public void unlock() {

    }
}
