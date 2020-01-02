package io.jopen;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author maxuefeng
 * @since 2019/12/3
 */
public class StorageBaiduBaike {

    private Settings settings = Settings.builder().put("cluster.name", "es-cluster").put("node.name", "node0").build();
    private TransportClient client = new PreBuiltTransportClient(settings)
            .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

    public StorageBaiduBaike() throws UnknownHostException {
    }

    private BlockingQueue<ImmutableMap<String, String>> dataQueue = new LinkedBlockingQueue<>(10000000);

    private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,
            15,
            100, TimeUnit.SECONDS,
            workQueue,
            new ThreadFactoryBuilder().setDaemon(true).setNameFormat("es-data-%d").build(),
            (runnable, executor) -> {
                try {
                    FutureTask<BulkResponse> futureTask = (FutureTask<BulkResponse>) runnable;
                    BulkResponse responses = futureTask.get();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    );

    private ListeningExecutorService listeningDecorator = MoreExecutors.listeningDecorator(threadPoolExecutor);


    @Test
    public void testCreateIndex() throws IOException {


        IndexResponse response = client.prepareIndex("everything", "_doc", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", "string")
                        .field("attrName", "string")
                        .field("desc", "string")
                        .endObject()
                ).get();

        System.err.println(response);
    }


    public void read(File file) throws IOException {

        Objects.requireNonNull(file);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String stemp;

        long line = 0L;

        List<Map<String, String>> data = new ArrayList<>();

        while ((stemp = bufferedReader.readLine()) != null) {

            String[] ret = stemp.split(",");
            if (ret.length == 3) {

                //
                if (data.size() >= 1000) {
                    try {
                        BulkRequestBuilder bulkRequest = client.prepareBulk();
                        for (Map<String, String> datum : data) {
                            bulkRequest.add(client.prepareIndex("everything", "_doc").setSource(datum));
                        }
                        BulkResponse itemResponses = bulkRequest.get();

                        line += data.size();


                        if (RestStatus.OK.equals(itemResponses.status())) {
                            System.err.println(String.format("数据同步状态 %s,已同步数量 %s", itemResponses.status().toString(), line));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    data.clear();
                }


                // 增加数据
                String name = ret[0];
                String attrName = ret[1];
                String desc = ret[2];
                Map<String, String> body =
                        ImmutableMap.of(
                                "name", name,
                                "attrName", attrName,
                                "desc", desc);

                data.add(body);
            }
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        StorageBaiduBaike storageBaiduBaike = new StorageBaiduBaike();
        storageBaiduBaike.read(new File(args[0]));
    }


    class Task implements Callable<BulkResponse> {

        private List<Map<String, String>> data;

        Task(List<Map<String, String>> data) {
            this.data = data;
        }

        @Override
        public BulkResponse call() throws Exception {

            BulkRequestBuilder bulkRequest = client.prepareBulk();
            for (Map<String, String> datum : data) {
                // 提交一个线程任务
                bulkRequest.add(client.prepareIndex("everything", "_doc")
                        .setSource(jsonBuilder()
                                .startObject()
                                .field("name", datum.get("name"))
                                .field("attrName", datum.get("attrName"))
                                .field("desc", datum.get("desc"))
                                .endObject()
                        )
                );
            }
            return bulkRequest.get();
        }
    }
}

