package io.jopen.db.redis.redisson.collection;

import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.mapreduce.RCollectionMapReduce;
import org.redisson.api.mapreduce.RCollectionMapper;
import org.redisson.api.mapreduce.RReducer;
import org.redisson.config.Config;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * {@link Redisson}
 *
 * @author maxuefeng
 * @since 2019/10/18
 */
public class DistributeCollectionTest {

    private RedissonClient client = null;

    @Before
    public void before() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://120.55.41.150:6379").setPassword("12233");
        client = Redisson.create(config);
    }

    /*generic set*/
    @Test
    public void testDistributeSet() throws InterruptedException {
        RSet<Object> genericSet = client.getSet("GenericSet");
        genericSet.forEach(System.err::println);
        System.err.println(String.format("not add ele size is %s", genericSet.size()));

        // 当元素被客户端主动删除时的回调函数
        genericSet.addListenerAsync((DeletedObjectListener) s -> System.err.println(String.format("当前元素 %s 被客户端主动删除", s)));

        // 当元素过期时的回调函数
        genericSet.addListenerAsync((ExpiredObjectListener) s -> System.err.println(String.format("当前元素 %s 马上过期", s)));
        /**
         * @see com.google.common.base.Joiner
         */
        // 对当前集合中的所有元素进行java8流式处理
        Object java8MapReduceRes = genericSet.stream()
                .map((Function<Object, Object>) Object::toString)
                .reduce((o, o2) -> Joiner.on("-").join(o, o2))
                .get();

        // 对当前元素进行大数据计算风格的流式处理
        RCollectionMapReduce<Object, Object, Object> collectionMapReduce = genericSet.mapReduce();
        collectionMapReduce
                // 进行map自定义元素操作
                .mapper((RCollectionMapper<Object, Object, Object>) (value, collector) -> collector.emit(UUID.randomUUID(), value))
                // 进行reduce自定义元素操作
                .reducer((RReducer<Object, Object>) (reducedKey, iter) -> null)
                // 设置计算超时时间
                .timeout(1000, TimeUnit.SECONDS);

        // 获取分布式锁
        RLock rLock = genericSet.getLock("readWriteLock");
        boolean tryLock = rLock.tryLock(1, 1, TimeUnit.SECONDS);
        // 如果获取成功则添加元素
        if (tryLock) {
            genericSet.add("an ele");
        }
        // 失败则自定义其他操作

    }

    /*sorted set*/
    @Test
    public void testDistributeSortedSet() {
        // client.getSet()
        RSortedSet<Object> sortedSet = client.getSortedSet("SortedSet");
        // sortedSet.addListenerAsync()
    }

    /*generic list*/
    @Test
    public void testDistributeList() {
    }

    /*generic map*/
    @Test
    public void testDistributeMap() {
    }

    @Test
    public void testDistributeQueue() {
    }
}
