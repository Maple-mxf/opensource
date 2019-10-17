package io.jopen.core.common.collection;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author maxuefeng
 * @since 2019/10/13
 */
public class BlockingQueueTest {


    BlockingQueue queue = new ArrayBlockingQueue(1000);

    @Test
    public void testPush() throws InterruptedException {
        while (true) {
            System.err.println("waiting   an  elements   avalible");


            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    queue.put("HelloWOrld");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            // blocking   线程
            System.err.println(queue.take());
        }
    }
}
