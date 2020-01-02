package io.jopen.core.other.csv;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * ES数据格式定义{name:String,attrName:String,desc:String}
 *
 * @author maxuefeng
 * @since 2019/12/3
 */
public class CSVReader {


    @Test
    public void testCreateIndex() throws IOException {
        Settings settings = Settings.builder().put("cluster.name", "es-cluster").put("node.name", "node0").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("114.67.245.63"), 9300));

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
        while ((stemp = bufferedReader.readLine()) != null) {
            String[] ret = stemp.split(",");
            if (ret.length == 3) {

            }
        }
    }

    @Test
    public void testRead() throws IOException {
        read(new File("C:\\other\\software\\ownthink_v2.csv"));
    }
}
