package io.jopen.distributelock;

/**
 * @author maxuefeng
 * @since 2019/10/21
 */
public class JedisDistributeLock implements DistributeLock {

    @Override
    public void lock() {

    }

    @Override
    public Boolean execute(Runnable runnable) {
        return null;
    }

    @Override
    public void unlock() {

    }
}
