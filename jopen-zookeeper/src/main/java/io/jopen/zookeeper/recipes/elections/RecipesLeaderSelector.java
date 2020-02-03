package io.jopen.zookeeper.recipes.elections;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.CancelLeadershipException;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <h4>Leader Election</h4>
 *
 * <pre>
 *     In distributed computing, leader election is the process of designating(委任) a single process as the organizer
 *     of some task distributed among several computers (nodes). Before the task is begun, all network nodes are
 *     unaware which node will serve as the "leader," or coordinator, of the task. After a leader election algorithm
 *     has been run, however, each node throughout the network recognizes a particular, unique node as the task leader.
 *
 *     NOTE: Curator has two leader election recipes. Which one to use depends on your requirements.
 * </pre>
 *
 * <pre>
 *     在分布式计算中，领导者选举是指将单个进程指定为分布在多台计算机（节点）之间的某个任务的组织者的过程。
 *     在任务开始之前，所有网络节点都不知道哪个节点将充当任务的“领导者”或协调器。然而，在运行了leader选举算法之后，
 *     整个网络中的每个节点都将一个特定的、唯一的节点识别为任务leader。
 *
 *     注：馆长有两个领导人选举食谱。使用哪一个取决于你的要求。
 * </pre>
 *
 * @author maxuefeng
 * @see org.apache.curator.framework.recipes.leader.LeaderSelector
 * @see LeaderSelectorListener  Notification for leadership
 * @see LeaderSelectorListenerAdapter 数据{@link LeaderSelectorListener}的默认实现
 * @see CancelLeadershipException
 * @since 2020/2/1
 */
public final
class RecipesLeaderSelector {

    private final String connectionString = "192.168.74.136:2181";
    // int baseSleepTimeMs, int maxRetries, int maxSleepM
    // 单位：毫秒
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20, 2000);

    private final CuratorFramework curatorClient;

    private final LeaderSelector leaderSelector;

    private final LeaderSelectorListener leaderSelectorListener = new LeaderSelectorListenerAdapter() {
        /**
         * 当前某个zk实例被授权为leader角色的时候，这个方法将返回开发者所希望的leader机器实例
         * Called when your instance has been granted leadership. This method
         * should not return until you wish to release leadership
         *
         *      By default, when {@link LeaderSelectorListener#takeLeadership(CuratorFramework)} returns, this
         *      instance is not requeued. Calling this method puts the leader selector into a mode where it
         *      will always requeue itself
         * @param client
         */
        public void takeLeadership(CuratorFramework client) {
            // requeue
            RecipesLeaderSelector.this.leaderSelector.autoRequeue();
        }
    };

    public RecipesLeaderSelector() {
        this.curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        // start the client
        this.curatorClient.start();
        // create leaderSelector instance
        this.leaderSelector = new LeaderSelector(this.curatorClient, "/leaderSelector", this.leaderSelectorListener);

    }

    /**
     * 指定选举leader的事件  如果在指定时间内获取不到leader机器 则中断线程（终止程序运行）
     *
     * @param mills
     * @throws InterruptedException
     */
    public void setupLeaderSelector(long mills) throws InterruptedException {
        // 1  start leaderSelector
        this.leaderSelector.start();

        // 2 sleep the main thread (睡眠当前线程  如果超过时间 还未出现leader角色机器  则interrupt main线程)
        Thread.sleep(mills);
        if (!this.leaderSelector.hasLeadership()) {
            Thread.interrupted();
        }

        // 3 close leaderSelector
        this.leaderSelector.close();


    }
}
