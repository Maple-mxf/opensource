package core.common.io;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import io.jopen.core.common.json.Json;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author maxuefeng
 */
public class UploadHelper {

    /**
     * TODO 注意设置超时时间
     */
    private static  OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(300000, TimeUnit.SECONDS)
            .readTimeout(300000, TimeUnit.SECONDS)
            .writeTimeout(300000, TimeUnit.SECONDS)
            .build();

    /**
     * @param url
     * @param file
     * @param token
     * @return
     */
    public static JSONObject upload(String url, File file, Map<String, String> token) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .addFormDataPart("createTime","2019:10:11 12:12:12")
                .build();

        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);

        token.forEach(builder::header);

        Request request = builder.build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            if (!response.isSuccessful()) {

                return Json.of("code", "0", "msg", "request Error");

            } else {

                return JSONObject.parseObject(jsonString);
            }
        } catch (IOException ex) {
            return Json.of("code", "0", "msg", ex.getMessage());
        }
    }

    /**
     * @param url
     * @param headers
     * @return
     */
    public static JSONObject upload(String url, byte[] bytes, Map<String, String> headers) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), bytes);

        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(fileBody);

        // header中
        headers.forEach(builder::header);

        Request request = builder.build();

        Response response;
        try {
            response = client.newCall(request).execute();
            String jsonString = response.body().string();
            if (!response.isSuccessful()) {


                return new JSONObject(ImmutableMap.of("code", "0", "msg", "request Error"));

            } else {

                return JSONObject.parseObject(jsonString);
            }
        } catch (IOException ex) {
            return new JSONObject(ImmutableMap.of("code", "0", "msg", ex.getMessage()));
        }
    }
}
