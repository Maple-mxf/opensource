package io.jopen.zookeeper.recipes.counters;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.recipes.shared.VersionedValue;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Executors;

/**
 * <h4>Shared Counter</h4>
 *
 * <pre>
 *     Manages a shared integer. All clients watching the same path will have the up-to-date
 *     value of the shared integer (considering ZK's normal consistency guarantees).
 *
 *     {@link SharedCount}
 *     {@link SharedCountReader}
 *     {@link org.apache.curator.framework.recipes.shared.SharedCountListener}
 * </pre>
 *
 * <pre>
 *     管理共享整数。监视同一路径的所有客户机都将拥有共享整数的最新值（考虑到ZK的正常一致性保证）
 * </pre>
 *
 * @author maxuefeng
 * @since 2020/2/3
 */
public final class RecipesSharedCounter {

    private final String connectionString = "192.168.74.136:2181";
    // int baseSleepTimeMs, int maxRetries, int maxSleepM
    // 单位：毫秒
    private final RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 20, 2000);

    private final CuratorFramework curatorClient;

    /**
     * Parameters:
     * client - the client
     * path - the shared path - i.e. where the shared count is stored
     * seedValue - the initial value for the count if/f the path has not yet been created
     *
     * @see SharedCount#SharedCount(CuratorFramework, String, int)
     */
    private final SharedCount sharedCount;

    private final String countPath = "/countPath";

    public RecipesSharedCounter() throws Exception {
        this.curatorClient = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        // start the client
        this.curatorClient.start();

        this.sharedCount = new SharedCount(curatorClient, countPath, 0);
        sharedCount.start();
    }

    public int getCount() {
        return this.sharedCount.getCount();
    }

    /**
     * Change the shared count value irrespective of its previous state
     *
     * @param newValue
     * @throws Exception
     */
    public void setCount(int newValue) throws Exception {
        this.sharedCount.setCount(newValue);
    }

    /**
     * Changes the shared count only if its value has not changed since the version specified by
     * newCount. If the count has changed, the value is not set and this client's view of the
     * value is updated. i.e. if the count is not successful you can get the updated value
     * by calling {@link #getCount()}.
     * <p>
     * 仅当共享计数的值自指定的版本之后没有更改时，才更改该计数
     * 。如果计数已更改，则不设置该值，并且此客户端对
     * 值已更新。如果计数不成功，您可以获取更新的值
     * 通过调用{@link SharedCount#getCount()}。
     *
     * @param previous
     * @param newCount
     * @throws Exception
     */
    public void trySetCount(VersionedValue<Integer> previous, int newCount) throws Exception {
        this.sharedCount.trySetCount(previous, newCount);
    }

    private SharedCountListener defaultListener = new SharedCountListener() {
        public void countHasChanged(SharedCountReader reader, int newCount) throws Exception {
            VersionedValue<Integer> versionedValue = reader.getVersionedValue();
            // current value
            Integer value = versionedValue.getValue();
            // current value
            int version = versionedValue.getVersion();
            // current value  value and currentNewValue as same
            int currentNewValue = newCount;
            System.err.println(currentNewValue);
        }

        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            System.err.println(String.format("connection state %s ", newState));
            //
            client.reconfig();

        }
    };

    public void addListener(SharedCountListener sharedCountListener) {
        this.sharedCount.addListener(sharedCountListener, Executors.newSingleThreadExecutor());
    }
}
