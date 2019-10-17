package io.jopen.core.common.util;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.jopen.core.common.Util;
import io.jopen.core.common.text.SequenceGeneratorID;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author maxuefeng
 */
public class SequenceGeneratorTest {

    public static ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

    public static Set<Long> snSet = Sets.newConcurrentHashSet();

    @Test
    public void testSimpleAPI() {

        SequenceGeneratorID sequenceGenerator = new SequenceGeneratorID();

        Set<Long> set = Sets.newHashSet();

        for (int i = 0; i < 1000000; i++) {

            long sn = sequenceGenerator.nextId();
            set.add(sn);
            System.err.println(sn);
        }

        System.err.println(set.size());
    }


    class Task implements Callable<Void> {

        SequenceGeneratorID sequenceGenerator;

        public Task(SequenceGeneratorID sequenceGenerator) {
            this.sequenceGenerator = sequenceGenerator;
        }

        @Override
        public Void call() {

            for (int j = 0; j < 1000000; j++) {

                long sn = sequenceGenerator.nextId();

                snSet.add(sn);
                System.err.println(sn);
            }

            return null;
        }
    }

    /**
     * 并发环境下生成sn
     */
    @Test
    public void testConcurrentOfSequenceGenerator() throws InterruptedException {

        SequenceGeneratorID sequenceGenerator = new SequenceGeneratorID();

        for (int i = 0; i < 20; i++) {
            executor.submit(new Task(sequenceGenerator));
        }

        // 睡眠20分钟
        Thread.sleep(1000 * 60 * 20);

        // size = 20000000
        System.err.println(snSet.size());
    }


    /**
     * 700
     * 576024390415401025
     * 576024390415401026
     *
     * 1000
     * 576024488760475719
     * 576024488760475720
     * 576024488760475721
     * @param args
     */
    public static void main(String[] args) {

        //
        SequenceGeneratorID sequenceGenerator = new SequenceGeneratorID(1000);

        //
        for (int i = 0; i < 100000; i++) {
            System.err.println(sequenceGenerator.nextId());
        }
    }
}
