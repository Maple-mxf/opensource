package io.jopen.distributelock.hazelcast;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.lock.FencedLock;
import org.junit.Before;
import org.junit.Test;

/**
 * @author maxuefeng
 * @see Config
 * @see HazelcastInstance
 * @since 2019/10/21
 */
public class HazelcastAPITest {

    // 配置对象
    private final Config config = new Config();

    // hazelcastInstance实例
    private HazelcastInstance hazelcastInstance;

    /**
     * <p>{@link ManagementCenterConfig#ManagementCenterConfig(String, int)}</p>
     *
     * @see Config
     */
    @Before
    public void before() {

        // 创建集群中心配置
        ManagementCenterConfig centerConfig = new ManagementCenterConfig();
        centerConfig.setUrl("http://127.0.0.1:8200/mancenter");
        centerConfig.setEnabled(true);

        // 设置config
        config.setInstanceName("test-center")
                .setManagementCenterConfig(centerConfig)
                .addMapConfig(
                        new MapConfig().setName("mapConfig")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                // 设置删除策略
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(20000)
                );

        // 创建hazelcastInstance实例
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }


    @Test
    public void testCreateFirstNode() {

        // 获取分布式锁
        FencedLock fencedLock = hazelcastInstance.getCPSubsystem().getLock("updateVideoStartLock");

        // 加锁
        boolean tryLock = fencedLock.tryLock();

        // 更新数据

        // 释放锁
        fencedLock.unlock();
    }
}
