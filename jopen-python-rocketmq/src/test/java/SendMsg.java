import com.google.common.util.concurrent.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * @author maxuefeng
 * @since 2020/1/5
 */
public class SendMsg {

    //Instantiate with a producer group name.
    private DefaultMQProducer producer;

    private String msg = "{\n" +
            "  \"id\":\"test\",\n" +
            "  \"typesOf\":\"generic\",\n" +
            "  \"storage\":\"alioss\",\n" +
            "  \"key\":\"googs.jpg\",\n" +
            "  \"bucket\":\"biz-qmbx\"\n" +
            "}";

    /**
     * 任务缓存队列
     * {@link LinkedBlockingQueue#LinkedBlockingQueue(int)}
     */
    private BlockingQueue<Runnable> taskCacheQueue = new LinkedBlockingQueue<>(20);

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            // 核心线程数量
            20,
            // 最大线程数量
            25,
            // 闲置线程的最大存活时间
            100, TimeUnit.SECONDS,
            // 任务缓存队列
            taskCacheQueue,
            // 线程创建工厂
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("test-bench-%d").build(),
            (runnable, executor) -> {
                // 如果任务提交失败，阻塞当前主线程
                executor.submit(runnable);
            }
    );

    private ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(threadPoolExecutor);


    @Before
    public void setup() throws MQClientException {
        this.producer = new DefaultMQProducer("please_rename_unique_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.0.101:9876");
        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
    }


    @Test
    public void pushMsg() throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        for (int i = 0; i < 22; i++) {
            ListenableFuture<SendResult> listenableFuture = listeningExecutorService.submit(new Task());

            Futures.addCallback(listenableFuture, new FutureCallback<SendResult>() {
                @Override
                public void onSuccess(@Nullable SendResult result) {
                    System.err.println(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.err.println(t.getMessage());
                }
            }, Executors.newFixedThreadPool(1));
        }
        Thread.sleep(10000000);
    }

    /**
     * @see TransferQueue
     */
    class Task implements Callable<SendResult> {

        @Override
        public SendResult call() throws Exception {

            for (int i = 0; i < 100000; i++) {
                //Create a message instance, specifying topic, tag and message body.
                Message message = new Message("ConsumptionCertImageInfo", "", msg.getBytes(RemotingHelper.DEFAULT_CHARSET));

                //Call send message to deliver message to one of brokers.
                SendResult sendResult = producer.send(message);
                // System.out.printf("%s %d ", sendResult, i);
                System.err.println(sendResult);
                System.err.println(i);
            }

            return null;
        }
    }


    @After
    public void end() {
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
