package io.jopen.core.common.concurrent;

import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * resize()方法的本质就是创建新的Entry数组，将原Map中的元素重新计算位置，加入到新的Map中。
 * 虽然死锁的成因是扩充时调用resize()方法，但真正的产生是发生在倒数第三行的transfer()方法中。
 * <p>
 * 这个过程不好理解，最好多读几遍，当产生带环链表后，如果调用get()方法，将会陷入死循环，CPU占用将达到100%。
 * 解决这一问题的方式有多种。比较low的可以使用HashTable和调用Collections工具类的synchronizedMap()
 * 方法达到线程安全的目的。但由于synchronized是串行执行，在访问量很大的情况下效率很低，不推荐使用。
 * 另外一种方式就是使用JUC包下的ConcurrentHashMap类
 * <p>
 * 链接：https://www.jianshu.com/p/4930801e23c8
 * <p>
 * HashMap的并发测试
 * <p>
 * 预期结果 :多个线程同时put 可能会造成get死循环
 * https://www.jianshu.com/p/4930801e23c8
 * <p>
 *
 * @author maxuefeng
 */
public class HashMapConcurrentTest {

    private static ExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));


    public static HashMap<String, Object> m = new HashMap<>();

    static class Put implements Callable<Void> {

        @Override
        public Void call() {

            for (int i = 0; i < 10000; i++) {

                String v = UUID.randomUUID().toString();
                m.put(String.valueOf(i), v);
                System.err.println("Put element [ " + i + " ] success, value [ " + v + " ]");
            }

            return null;
        }
    }

    static class Get implements Callable<Void> {
        @Override
        public Void call() {

            for (int i = 0; i < 10000; i++) {

                Object v = m.get(String.valueOf(i));

                System.err.println("Get element [ " + i + " ] success, value [ " + v + " ]");
            }
            return null;
        }
    }

    /**
     * 测试结果 多个线程同时Put  多个线程同时Get   会造成Get不出来元素的结果
     *
     * @throws InterruptedException
     */
    @Test
    public void testConcurrent() throws InterruptedException {

        service.submit(new Put());
        service.submit(new Put());
        service.submit(new Get());
        service.submit(new Put());
        service.submit(new Get());

        Thread.sleep(10000000);
    }
}
