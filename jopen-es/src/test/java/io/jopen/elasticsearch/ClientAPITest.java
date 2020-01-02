package io.jopen.elasticsearch;

import com.google.common.collect.ImmutableBiMap;
import io.jopen.matcher.SearchResult;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author maxuefeng
 * @since 2019/11/26
 */
public class ClientAPITest {

    @Test
    public void testCreateIndex() throws IOException {
        Settings settings = Settings.builder().put("cluster.name", "es-cluster").put("node.name", "node0").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("114.67.246.62"), 9300));

        IndexResponse response = client.prepareIndex("commodities", "_doc", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("cid", "kimchy")
                        .field("name", "commodity_name")
                        .endObject()
                ).get();

        System.err.println(response);
    }

    // save data
    @Test
    public void addItems() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "es_cluster").put("node.name", "node0").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("114.67.246.62"), 9300));
        ImmutableBiMap<String, ? extends Serializable> body =
                ImmutableBiMap.of("user", "jack1", "postDate", new Date(), "message", "I Like Message1");
        IndexResponse response = client.prepareIndex("twitter.monitoring", "_doc").setSource(body).get();
        System.err.println(response);
    }

    public List<SearchResult> testSearch() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "es-cluster").put("node.name", "node0").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("114.67.246.62"), 9300));

        SearchResponse response = client.prepareSearch("twitter.monitoring")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("user", "jack1"))                 // Query
                .setFrom(0).setSize(60).setExplain(true)
                .get();

        List<SearchResult> searchResultList = new ArrayList<>();
        response.getHits().iterator().forEachRemaining(documentFields -> {
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            float score = documentFields.getScore();
            searchResultList.add(new SearchResult(sourceAsMap, score));
        });
        return searchResultList;
    }




}
