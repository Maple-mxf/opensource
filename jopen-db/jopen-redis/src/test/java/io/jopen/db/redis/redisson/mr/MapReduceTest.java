package io.jopen.db.redis.redisson.mr;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.RCollectionMapper;
import org.redisson.api.mapreduce.RReducer;
import org.redisson.config.Config;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maxuefeng
 * @since 2019/10/21
 */
public class MapReduceTest {


    private RedissonClient client = null;

    @Before
    public void before() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.74.136:6379");
        client = Redisson.create(config);
    }

    @Test
    public void testSimpleAPI() throws InterruptedException {

        RList<String> rList = client.getList("MapReduceList");

        // 放入元素
        rList.add("Hello");
        rList.add("Hello");
        rList.add("spark");

        Map<Object, Object> mapReduceResult = rList.mapReduce()

                // map (word->count)
                .mapper((RCollectionMapper<String, Object, Object>) (value, collector) ->

                        // key表示字符串 value表示出现的pinlv
                        collector.emit(value, 1))

                // reduce (sum)
                .reducer((RReducer<Object, Object>) (reducedKey, iter) -> {

                    // 总和
                    AtomicInteger sum = new AtomicInteger();

                    // 将字符串出现的次数进行汇总相加
                    iter.forEachRemaining(object -> sum.addAndGet((Integer) object));

                    // 返回当前字符出现的次数
                    return sum.get();
                }).execute();

        System.err.println(mapReduceResult);
    }
}
