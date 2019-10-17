package io.jopen.core.common.net.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Apache apollo 是基于ActiveMQ的中间件,支持MQTT协议
 * <p>
 * Apache apollo    run command :[/usr/local/software/apache-apollo-1.7.1/mybroker/bin/apollo-broker run]
 * <p>
 * org.eclipse.paho.client.mqttv3
 *
 * @author maxuefeng
 */
public class MQTTClientTest {

    // MQTT消息中心地址
    private String broker;

    // 订阅/发布主题
    private String topic;

    // 登录用户名
    private String username;

    // 登录密码
    private String password;


    /**
     * MQTT协议等级
     * <p>
     * MQTT支持三种QOS等级
     * <p>
     * QOS0: "最多一次",消息发布完全依赖于TCP/IP网络.分发的消息可能丢失或重复.
     * 例如,这个等级可用于环境传感器数据,单次的数据丢失没关系,因为不久后还会有
     * 第二次发送
     * <p>
     * QOS1: "至少一次",确保消息可以到达,因为消息可能会重复
     * <p>
     * QOS2: "只有一次",确保消息只到达一次,例如这个等级可以用在一个计费系统中,
     * 这里如果消息重复或者丢失或导致不正确的计费
     */
    private int qos = 1;


    @Before
    public void setup() {

        this.broker = "tcp://0.0.0.0:61613";

        this.topic = "simpleTopic";

        this.username = "admin";

        this.password = "password";
    }

    @Test
    public void testSimplePublishTopic() throws MqttException {

        // 客户端标识
        String clientId = "publishClient0";

        // 发送的一段文本
        String contentTxt = "MQPP Server is Apache apollo....Welcome entrance '物联网时代,未来已来!'";

        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());

        // 设置连接参数
        MqttConnectOptions op = new MqttConnectOptions();
        //
        op.setUserName(this.username);
        //
        op.setPassword(password.toCharArray());
        //
        op.setCleanSession(false);

        client.connect(op);

        client.publish(this.topic, new MqttMessage(contentTxt.getBytes(Charset.forName("UTF-8"))));

        client.disconnect();

        client.close();
    }

    @Test
    public void testSimpleSubscribeTopic() throws MqttException, InterruptedException {

        // 客户端标识
        String clientId = "subscribeClient0";

        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());

        // 设置连接参数
        MqttConnectOptions op = new MqttConnectOptions();
        //
        op.setUserName(this.username);
        //
        op.setPassword(password.toCharArray());
        //
        op.setCleanSession(false);
        //
        op.setConnectionTimeout(10);

        // 服务端每个20S向客户端发送心跳检查
        op.setKeepAliveInterval(20);

        // 设置回调函数
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.err.println("Lost connection....   " + throwable.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                System.err.println("Topic Name: " + topic);

                System.err.println("From Topic Message: " + new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                System.err.println("Complete...Success !");
            }
        });

        client.connect(op);

        client.subscribe(topic, qos);

        Thread.sleep(10000000);

    }
}

























