package io.jopen.zookeeper.recipes.elections;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;

/**
 * In distributed computing, leader election is the process of designating a single process as
 * the organizer of some task distributed among several computers (nodes). Before the task is begun,
 * all network nodes are unaware which node will serve as the "leader," or coordinator, of the task.
 * After a leader election algorithm has been run, however, each node throughout the network recognizes a particular,
 * unique node as the task leader.
 *
 * <pre>
 *     在分布式计算中，leader选举是多个进程进行相互配合的一个过程，在任务开始前，每台机器都是一个leader角色（此时没有flower）
 *     当leader选举算法进行完成的时候，诸多机器会选举出仅有一个leader角色的机器
 *     {@link org.apache.curator.framework.recipes.leader.LeaderLatch}
 * </pre>
 *
 *
 *
 *
 * <p>
 * NOTE: Curator has two leader election recipes. Which one to use depends on your requirements.
 *
 * @author maxuefeng
 * @see java.util.concurrent.CountDownLatch 此类的原理类似
 * @since 2020/2/1
 */
public final
class RecipesLeaderLatch {

    private final String connectionString = "192.168.74.136:2181";
    // int baseSleepTimeMs, int maxRetries, int maxSleepM
    // 单位：毫秒
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20, 2000);

    private final CuratorFramework curatorClient;

    private final LeaderLatch leaderLatch;

    public RecipesLeaderLatch() {
        this.curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        // start the client
        this.curatorClient.start();
        // choose root path
        this.leaderLatch = new LeaderLatch(this.curatorClient, "/leader");

    }


    // leaderLatch的核心操作API只有两个；start和close，当启动LeaderLatch的时候，LeaderLatch会协商所有的节点进行选举算法
    // 所有的使用复杂性都封装在了内部，中间会block线程，使得外界调用API会处于一个等待的状态。

    /**
     * LeaderLatches must be started:
     *
     * @throws Exception if occur
     * @see LeaderLatch#start()
     */
    public void start() throws Exception {
        // start leaderLatch
        this.leaderLatch.start();
    }

    // Once started, the LeaderLatch will negotiate with any other LeaderLatch participants
    // that use the same latch path and randomly choose one of them to be the leader.
    // At any time, you can determine if a given instance is the leader by calling:

    /**
     * @throws IOException
     * @throws InterruptedException
     * @see LeaderLatch#hasLeadership()
     */
    public void negotiate() throws IOException, InterruptedException {

        // 获取协商结果的值
        boolean hasLeadership = this.leaderLatch.hasLeadership();

        while (hasLeadership) {
            this.leaderLatch.await();
            hasLeadership = this.leaderLatch.hasLeadership();
        }

        // 协商完毕 退出选举算法任务
        this.leaderLatch.close();
    }
}
