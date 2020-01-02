package io.jopen.matcher;

import com.google.common.collect.ImmutableBiMap;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 封装此类 方便Python调用
 *
 * @author maxuefeng
 * @since 2019/11/28
 */
public final class KeyWordMatcher {
    private Settings settings;
    private TransportClient client;

    public KeyWordMatcher(String clusterName, String[] nodeNames, Map<String, Integer> ipAndPorts) throws UnknownHostException {

        Settings.Builder builder = Settings.builder();
        builder.put("cluster.name", clusterName);
        for (String nodeName : nodeNames) {
            builder.put("node.name", nodeName);
        }
        settings = builder.build();

        client = new PreBuiltTransportClient(settings);
        for (Map.Entry<String, Integer> ip_port : ipAndPorts.entrySet()) {
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(ip_port.getKey()), ip_port.getValue()));
        }
    }

    /**
     * @param indices     索引
     * @param type        所属类型
     * @param _id         id值
     * @param json_source json键值对
     * @return return status code 200-300 success
     */
    public int insertItem(String indices, String type, String _id, String json_source) {
        IndexResponse response = client.prepareIndex(indices, type, _id).setSource(json_source, XContentType.JSON).get();
        System.err.println(response);
        return response.status().getStatus();
    }

    public List<SearchResult> search(String indices, String keyword) {

        SearchResponse response = client.prepareSearch(indices)
                //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("name", keyword))                 // Query
                .setFrom(0).setSize(10).setExplain(true)
                .get();
        List<SearchResult> searchResultList = new ArrayList<>();
        response.getHits().iterator().forEachRemaining(documentFields -> {
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            float score = documentFields.getScore();
            searchResultList.add(new SearchResult(sourceAsMap, score));
        });

        // 过滤searchResultList
        List<SearchResult> ret = searchResultList.stream()
                .sorted((o1, o2) -> {
                    if (o1.getScore() > o2.getScore()) {
                        return -1;
                    } else if (o1.getScore().equals(o2.getScore())) {
                        return 0;
                    } else {
                        return 1;
                    }
                })
                .limit(3)
                .collect(Collectors.toList());
        System.err.println(ret);
        return searchResultList;
    }

    public void deleteIndex(String indices) {
        DeleteResponse response = client.prepareDelete().get();
        System.err.println(response);
    }

    public static void main(String[] args) throws UnknownHostException, SQLException {
        KeyWordMatcher keyWordMatcher = new KeyWordMatcher("es-cluster", new String[]{"node0"}, ImmutableBiMap.of("114.67.246.62", 9300));
        // "commodities", "_doc"

        //
        /*List<Map<String, Object>> mapList = JDBCUtils.query("select id as cid,name from commodity");
        mapList.forEach(t -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cid", t.get("id"));
            jsonObject.put("name", t.get("name"));
            keyWordMatcher.insertItem("commodities", "_doc", UUID.randomUUID().toString().replaceAll("-", ""), jsonObject.toJSONString());
        });*/
        // keyWordMatcher.deleteIndex("commodities");
        List<SearchResult> searchResults = keyWordMatcher.search("commodities", "调");
        System.err.println(searchResults);
    }
}
