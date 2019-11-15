package io.jopen.mapreduce.redisson;

import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.mapreduce.*;
import org.redisson.config.Config;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maxuefeng
 * @since 2019/11/14
 */
public class RedissonMapReduce {


    private RedissonClient client = null;

    public RedissonMapReduce() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.74.136:6379");
        client = Redisson.create(config);
    }

    public void countWord() {

        RList<String> rList = client.getList("MapReduceList");

        // 放入元素
        // rList.add("Hello");
        // rList.add("Hello");
        // rList.add("spark");

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
                }).timeout(100, TimeUnit.MILLISECONDS).execute();

        System.err.println(mapReduceResult);
    }

    public static class WordMapper implements RMapper<String, String, String, Integer> {

        @Override
        public void map(String key, String value, RCollector<String, Integer> collector) {
            String[] words = value.split(" ");
            for (String word : words) {
                collector.emit(word, 1);
            }
        }
    }

    public static class WordReducer implements RReducer<String, Integer> {

        @Override
        public Integer reduce(String reducedKey, Iterator<Integer> iter) {
            int sum = 0;
            while (iter.hasNext()) {
                Integer i = iter.next();
                sum += i;
            }
            return sum;
        }
    }

    /**
     * Collator对象统计所有单词的使用情况。
     */
    public static class WordCollator implements RCollator<String, Integer, Integer> {
        @Override
        public Integer collate(Map<String, Integer> resultMap) {
            int result = 0;
            for (Integer count : resultMap.values()) {
                result += count;
            }
            return result;
        }
    }

    public static void main(String[] args) {
        RedissonMapReduce redissonMapReduce = new RedissonMapReduce();
        RMap<String, String> map = redissonMapReduce.client.getMap("wordsMap");
        map.put("line1", "Alice was beginning to get very tired");
        map.put("line2", "of sitting by her sister on the bank and");
        map.put("line3", "of having nothing to do once or twice she");
        map.put("line4", "had peeped into the book her sister was reading");
        map.put("line5", "but it had no pictures or conversations in it");
        map.put("line6", "and what is the use of a book");
        map.put("line7", "thought Alice without pictures or conversation");

        RMapReduce<String, String, String, Integer> mapReduce = map.<String, Integer>mapReduce()
                .mapper(new WordMapper())
                .reducer(new WordReducer());

        // 统计词频
        Map<String, Integer> mapToNumber = mapReduce.execute();
        System.err.println(mapToNumber);

        // 统计字数
        Integer totalWordsAmount = mapReduce.execute(new WordCollator());
        System.err.println(totalWordsAmount);
    }
}
