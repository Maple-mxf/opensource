package io.jopen.rocketmq;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.UUID;


/**
 * @author maxuefeng
 * @since 2019-05-28
 */
public class GenericPublisher {

    private Logger L = LoggerFactory.getLogger(GenericPublisher.class);


    // 生产者的组名
    private String producerGroup;

    //
    private DefaultMQProducer producer;

    //  NameServer 地址
    private String namesrvAddr;

    /*mq地址   group*/
    public GenericPublisher(String namesrvAddr, String producerGroup) throws MQClientException {

        this.namesrvAddr = namesrvAddr;

        this.producerGroup = producerGroup;

        this.producer = new DefaultMQProducer(producerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);

        //
        producer.setVipChannelEnabled(false);

        String instanceName = "RocketMQ-" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        producer.setInstanceName(instanceName);

        producer.start();
        L.info("produce starting.....");
    }

    public SendResult publish(String topic, String body) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        return this.publish(topic, "", body, 0);
    }

    public SendResult publish(String topic, String body, int delayTimeLevel) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        return this.publish(topic, "", body, delayTimeLevel);
    }

    public SendResult publish(String topic, String tags, String body, int delayTimeLevel) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {

        Message message = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));

        message.setDelayTimeLevel(delayTimeLevel);

        StopWatch stop = new StopWatch();

        stop.start();

        SendResult result = producer.send(message);

        L.info("Send msg Topic {}, tags {}, msg {} ,", topic, tags, body);

        L.info("Send status {} ,", result.getSendStatus());

        stop.stop();

        return result;
    }

}
