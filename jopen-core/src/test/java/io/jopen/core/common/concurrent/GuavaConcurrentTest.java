package io.jopen.core.common.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author maxuefeng
 */
public class GuavaConcurrentTest {

    //
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,
            15,

            //
            100, TimeUnit.SECONDS,

            //
            workQueue,

            //
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("es-data-%d").build(),

            //
            new RejectedExecutionHandler(){
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    executor.purge();
                }
            }
    );

    @Test
    public void testCreateThreadPool() {

    }
}
