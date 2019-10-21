package io.jopen.distributelock.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @see InterProcessSemaphoreMutex
 * @see InterProcessSemaphoreV2
 * @see InterProcessReadWriteLock
 * @since 2019/10/21
 */
public class ZookeeperCuratorDistributeLockImpl {

    private final String zkQurom = "192.168.74.136:2181";

    // ZooKeeper 锁节点路径, 分布式锁的相关操作都是在这个节点上进行
    private final String lockNameSpace = "/distributeLockDir";

    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    private final CuratorFramework zkClient =
            CuratorFrameworkFactory.newClient(
                    zkQurom,
                    5000,
                    3000,
                    retryPolicy);

    @Before
    public void before() {
        // 启动客户端
        zkClient.start();
    }

    @Test
    public void curatorForZKDistributeLock() throws Exception {
        // 创建共享锁
        InterProcessLock lock = new InterProcessSemaphoreMutex(zkClient, lockNameSpace);

        // 进行加锁
        lock.acquire();

        // 执行数据修改操作或者说是更新操作

        // 释放锁
        lock.release();
    }

    @After
    public void close() {
        CloseableUtils.closeQuietly(zkClient);
    }
}
