package io.jopen.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019-05-28
 */
public abstract class ConcurrentMsgListener implements MessageListenerConcurrently {

    private Logger L = LoggerFactory.getLogger(ConcurrentMsgListener.class);

    protected String topic;

    protected String subExpression;

    protected String namesrvAddr;

    protected String consumerGroup;

    protected GenericSubscriber genericSubscriber;

    /*/*String topic, String subExpression, String namesrvAddr, String consumerGroup*/
    public ConcurrentMsgListener(String topic, String subExpression, String namesrvAddr, String consumerGroup) {
        this.topic = topic;
        this.subExpression = subExpression;
        this.namesrvAddr = namesrvAddr;
        this.consumerGroup = consumerGroup;
        this.genericSubscriber = new GenericSubscriber(namesrvAddr, consumerGroup, topic, subExpression, this);
    }

    public GenericSubscriber getSubscriber() {
        return this.genericSubscriber;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        // 会把不同的消息分别放置到不同的队列中
        for (Message msg : messageExtList) {

            L.info("Receive msg Topic {}, subExpression {}, msg {} ", topic, subExpression, new String(msg.getBody()));

            // 接收到消息之后解析消息
            handlerSingleMsg(msg);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public abstract void handlerSingleMsg(Message msg);
}
