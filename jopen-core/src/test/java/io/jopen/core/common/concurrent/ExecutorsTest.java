package io.jopen.core.common.concurrent;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @author maxuefeng
 * @since 2019/8/28
 */
public class ExecutorsTest {

    @Test
    public void simpleTest() {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

        ListenableFuture<?> future = service.submit((Callable<Void>) () -> {
            System.err.println("=====");
            return null;
        });

        future.addListener(() -> System.err.println("I am listen the callable thread"), service);
    }
}
