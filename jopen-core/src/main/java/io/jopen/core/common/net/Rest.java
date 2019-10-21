package io.jopen.core.common.net;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * rest restClient
 * <p>{@link okhttp3.OkHttpClient}</p>
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public class Rest {

    private final OkHttpClient restClient;

    public Rest() {
        restClient = new OkHttpClient();
    }

    /**
     * 设置复杂的客户端配置
     *
     * @param builder 构建参数
     */
    public Rest(OkHttpClient.Builder builder) {
        restClient = builder.build();
    }

    public <T> T getForEntity(String url, Class<T> responseType) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        Response response = restClient.newCall(request).execute();
        String body = response.body().string();
        if (responseType.equals(String.class)) {
            return (T) body;
        }

        if ("application/json".equals(response.headers("responseType"))) {
            JSONObject jsonObject = JSONObject.parseObject(body);

        }
        return null;
    }

    public void postForEntity() {
    }


}
