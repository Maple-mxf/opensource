package io.jopen.distributelock;


import com.google.common.base.Preconditions;

/**
 * <p>{@link Runnable}</p>
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public interface DistributeLock {

    void lock();

    /**
     * @param runnable other consumer
     * @return success or failure
     */
    Boolean execute(Runnable runnable);


    void unlock();

    static DistributeLock type(Class type) {
        Preconditions.checkNotNull(type);
        if (type.equals(RedissonDistributeLock.class)) {
            return null;
        }
        return null;
    }
}
