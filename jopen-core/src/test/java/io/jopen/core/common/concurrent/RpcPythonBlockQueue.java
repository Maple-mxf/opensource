package io.jopen.core.common.concurrent;

import io.jopen.core.common.json.Json;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.*;

public class RpcPythonBlockQueue {

    LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    ExecutorService executorService = new ThreadPoolExecutor(
            20,
            30,
            10,
            TimeUnit.HOURS,
            queue);

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder().get().url("http://localhost:9090/push").build();

    @Test
    public void testStart() throws InterruptedException {


        for (int k = 0; k < 100; k++) {
            executorService.submit((Callable<Void>) () -> {
                //
                for (int i = 0; i < 10; i++) {
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            System.err.println(response.body().string());
                        }
                    });
                }

                return null;
            });
        }

        Thread.sleep(2000000);
    }


    public static void main(String[] args) {
        System.err.println(Json.of("k","v").toJSONString());
    }
}
