package io.jopen.python.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author maxuefeng
 * @since 2020/1/4
 */
public class PythonRocketMQAdapter {

    private BlockingQueue<String> dataCache = new LinkedBlockingQueue<>(200000);

    private volatile boolean continueConsume = false;

    public PythonRocketMQAdapter(String namesrvAddr, String topic) throws MQClientException {

        // consumerGroup
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("python-consumer-image");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic, "*");
        consumer.setConsumeThreadMax(1);
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener(new Callback());

        // start
        consumer.start();
    }

    class Callback implements MessageListenerOrderly {
        @Override
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            updateConsumeState(false);
            List<String> jsonList = msgs.stream().map(msg -> new String(msg.getBody(), StandardCharsets.UTF_8)).collect(Collectors.toList());

            for (String jsonStr : jsonList) {
                try {
                    dataCache.put(jsonStr);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 阻塞主线程
            while (!continueConsume) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    public String take() throws InterruptedException {
        return this.dataCache.take();
    }

    /**
     * 提供Python外部调用  进行限制流量
     *
     * @param value
     */
    public void updateConsumeState(boolean value) {
        this.continueConsume = value;
    }


}
