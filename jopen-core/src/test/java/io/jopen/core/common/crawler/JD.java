package io.jopen.core.common.crawler;

import io.jopen.core.common.json.Json;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JD {

    OkHttpClient client = new OkHttpClient.Builder().build();


    Map<String, String> header = new HashMap<>();


    public JD() {
        header.put(":authority", "search.jd.com");
        header.put(":method", "GET");
        header.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        header.put("scheme", "https");
        header.put("accept-encoding", "gzip, deflate, br");
        header.put("accept-language", "zh-CN,zh;q=0.9");
        header.put("referer", "https://www.jd.com/");
        header.put("upgrade-insecure-requests", "1");
        header.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");

        // TODO
        header.put("cookie", "__jda=122270672.206052292.1562421602.1562421602.1563716813.1; __jdb=122270672.1.206052292|1.1563716813; __jdc=122270672; __jdv=122270672|direct|-|none|-|1563716812857; areaId=1; ipLoc-djd=1-0-0-0; __jdu=206052292; PCSYCityID=1; shshshfp=6c71e09d9061106f7ee71c47a4290e9a; shshshfpa=80588046-1be1-0b43-578f-a9f2efa0adaf-1563716814; shshshsID=bb7a60b24406a2a503e9d2944c7c6d28_1_1563716814278; shshshfpb=wLKkTYMfoks9rPdtSzgyPJg%3D%3D");
        header.put(":path", "/Search?keyword=%E7%94%B5%E8%84%91%E5%8C%85&enc=utf-8&pvid=56f7f2f658af405988feede923f40ef6");
    }


    @Test
    public void testJD() throws IOException {

        /**
         * keyword: 京东
         * enc: utf-8
         * pvid: 46f7e2aecbd54bb5bb9a05dcc80c3552
         */
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                Json.of("keyword", "京东", "enc", "utf-8", "pvid", "46f7e2aecbd54bb5bb9a05dcc80c3552")
                        .toJSONString());

        Request request = new Request.Builder()
                .url("https://search.jd.com/Search?keyword=%E4%BA%AC%E4%B8%9C&enc=utf-8&pvid=46f7e2aecbd54bb5bb9a05dcc80c3552")
                .post(requestBody).headers(Headers.of(header))
                .build();

        Response response = client.newCall(request).execute();

        String string = response.body().string();

        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        System.err.println(new String(bytes));
    }
}
