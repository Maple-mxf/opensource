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
public class HazelcastDistributeLockImpl {

    // 配置对象
    private final Config config = new Config();

    // hazelcastInstance实例
    private HazelcastInstance hazelcastInstance1;
    private HazelcastInstance hazelcastInstance2;

    /**
     * <p>{@link ManagementCenterConfig#ManagementCenterConfig(String, int)}</p>
     *
     * @see Config
     */
    @Before
    public void before() {

        // 创建集群中心配置
        ManagementCenterConfig centerConfig = new ManagementCenterConfig();
        //centerConfig.setUrl("http://127.0.0.1:8200/mancenter");
        centerConfig.setEnabled(true);

        // 设置config
        //config.setInstanceName("test-center")
        config.setManagementCenterConfig(centerConfig)
                .addMapConfig(
                        new MapConfig().setName("mapConfig")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                // 设置删除策略
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(20000)
                );
        config.getCPSubsystemConfig().setCPMemberCount(3);

        // 创建hazelcastInstance实例
        hazelcastInstance1 = Hazelcast.newHazelcastInstance(config);
        hazelcastInstance2 = Hazelcast.newHazelcastInstance(config);
    }


    @Test
    public void HazelcastdistributeLock() {

        // 获取分布式锁
        FencedLock fencedLock1 = hazelcastInstance1.getCPSubsystem().getLock("updateVideoStartLock");
        FencedLock fencedLock2 = hazelcastInstance2.getCPSubsystem().getLock("updateVideoStartLock");

        // 加锁
        boolean tryLock = fencedLock1.tryLock();
        System.err.println(tryLock);

        // 其他安全操作

        // 释放锁
        fencedLock1.unlock();
        boolean tryLock1 = fencedLock2.tryLock();
        System.err.println(tryLock1);
    }
}
