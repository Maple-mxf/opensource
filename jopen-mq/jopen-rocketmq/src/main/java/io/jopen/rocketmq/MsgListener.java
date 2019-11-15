package io.jopen.rocketmq;

import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author maxuefeng
 * @since 2019-06-18
 */
public abstract class MsgListener implements MessageListenerOrderly {

    private Logger L = LoggerFactory.getLogger(MsgListener.class);

    protected String topic;

    protected String subExpression;

    protected String namesrvAddr;

    protected String consumerGroup;

    protected OrderlySubscriber genericSubscriber;

    /*/*String topic, String subExpression, String namesrvAddr, String consumerGroup*/
    public MsgListener(String topic, String subExpression, String namesrvAddr, String consumerGroup) {
        this.topic = topic;
        this.subExpression = subExpression;
        this.namesrvAddr = namesrvAddr;
        this.consumerGroup = consumerGroup;
        this.genericSubscriber = new OrderlySubscriber(namesrvAddr, consumerGroup, topic, subExpression, this);
    }

    public OrderlySubscriber getSubscriber() {
        return this.genericSubscriber;
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {

        for (Message msg : messageExtList) {

            L.info("Receive msg Topic {}, subExpression {}, msg {} ", topic, subExpression, new String(msg.getBody()));

            // 接收到消息之后解析消息
            handlerSingleMsg(msg);
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }

    public abstract void handlerSingleMsg(Message msg);
}
