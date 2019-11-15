package io.jopen.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author maxuefeng
 * @since 2019-05-28
 */
public class GenericSubscriber {

    private Logger L = LoggerFactory.getLogger(GenericSubscriber.class);

    private String namesrvAddr;

    private DefaultMQPushConsumer consumer;

    // such as tag1 || tag2  if null subscribe all
    private String subExpression;

    // 订阅的主题为OCR请求主题
    private String topic;

    private MessageListenerConcurrently listener;

    public GenericSubscriber(String namesrvAddr, String consumerGroup, String topic, String subExpression, MessageListenerConcurrently listener) {

        this.namesrvAddr = namesrvAddr;
        this.topic = topic;
        this.subExpression = subExpression;
        this.listener = listener;
        assert listener != null;
        consumer = new DefaultMQPushConsumer(consumerGroup);
    }

    /**
     * 初始化RocketMq的监听信息，渠道信息
     */
    public void messageListener() {

        consumer.setNamesrvAddr(namesrvAddr);

        try {
            // 订阅PushTopic下Tag为push的消息,都订阅消息
            consumer.subscribe(topic, subExpression);

            // 程序第一次启动从消息队列头获取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            // 可以修改每次消费消息的数量，默认设置是每次消费一条
            consumer.setConsumeMessageBatchMaxSize(1);

            // 在此监听中消费信息，并返回消费的状态信息
            consumer.registerMessageListener(listener);

            consumer.start();

            L.info("consumer started");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
