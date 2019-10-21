package io.jopen.db.redis.redisson.mr;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author maxuefeng
 * @since 2019/10/21
 */
public class MapReduceTest {


    private RedissonClient client = null;

    @Before
    public void before() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.15.41.150:6379").setPassword("12323213");
        client = Redisson.create(config);
    }

    @Test
    public void testSimpleAPI() throws InterruptedException {
        RBlockingQueue<Object> blockingQueue = client.getBlockingQueue("testQueue");

        while (true){
            Object ele = blockingQueue.take();

        }
    }
}
