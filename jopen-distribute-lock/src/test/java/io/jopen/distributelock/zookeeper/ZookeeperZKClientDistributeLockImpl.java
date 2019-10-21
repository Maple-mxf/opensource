package io.jopen.distributelock.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author maxuefeng
 * @since 2019/10/21
 */
public class ZookeeperZKClientDistributeLockImpl {

    private final String zkQurom = "192.168.74.136:2181";
    private ZooKeeper zkClient;
    private String lockNameSpace = "/distributeLockDir";


    @Before
    public void before() throws IOException {
        zkClient = new ZooKeeper(zkQurom, 6000, event -> {
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                System.out.println("connection is established...");
            }
        });
    }

    @Test
    public void lockRootPath() throws KeeperException, InterruptedException {
        // 如果路径不存在则会返回null
        Stat stat = zkClient.exists(lockNameSpace, true);

        // 如果不存在则创建节点
        if (stat == null) {
            zkClient.create(lockNameSpace, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }



    }
}
