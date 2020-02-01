package io.jopen.zookeeper.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2020/2/1
 */
public class CuratorBaseApi {

    private final String connectionString = "192.168.74.136:2181";
    private final RetryPolicy retryPolicy = new RetryOneTime(2000);

    private CuratorFramework curatorClient;

    @Before
    public void setupClient() {
        curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        curatorClient.start();
    }

    @Test
    public void testWatch() {

    }
}
