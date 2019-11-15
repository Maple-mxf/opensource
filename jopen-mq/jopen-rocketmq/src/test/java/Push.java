import com.alibaba.fastjson.JSONObject;
import io.jopen.core.common.json.Json;
import io.jopen.core.common.text.Worker;
import io.jopen.rocketmq.GenericPublisher;
import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author maxuefeng
 * @since 2019/11/15
 */
public class Push {

    // (241/1024) KB  ->0.2353515625KB
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, UnsupportedEncodingException, MQBrokerException {

        // (id string,uid string,buy_ts bigint,upload_ts bigint,price double,items array<string>);
        JSONObject object = Json.of(
                "id", Worker.id() + "C",
                "uid", "U" + Worker.id(),
                "buy_ts", new Date().getTime(),
                "upload_ts", new Date().getTime(),
                "price", RandomUtils.nextDouble(10, 10000),
                "items", new String[]{"item" + Worker.id(), "item" + Worker.id(), "item" + Worker.id(), "item" + Worker.id(),}
        );
        System.err.println(object.toJSONString());

        System.err.println(object.toJSONString().getBytes().length);

        double sum = 0;
        GenericPublisher publisher = new GenericPublisher("192.168.74.136:9876", "jopen-rocketmq");
        for (int i = 0; i < 100000000; i++) {
            sum += 0.2353515625D;
            publisher.publish("cert", object.toJSONString());
            System.err.println("已灌输" + (sum / 1000D) + " MB");
        }

        System.err.println(241D / 1024);
    }
}
