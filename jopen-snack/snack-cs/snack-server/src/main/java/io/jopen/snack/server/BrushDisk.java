package io.jopen.snack.server;

import com.google.common.util.concurrent.FutureCallback;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * 异步刷盘任务  将临时文件落地
 *
 * @author maxuefeng
 * @since 2019/10/23
 */
public class BrushDisk extends Task<Boolean> {

    public BrushDisk(String obj) {
    }


    @Override
    public Boolean call() throws Exception {
        return null;
    }

    @Override
    public FutureCallback<Boolean> completeCallback() {

        return new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean o) {
                // 任务完成
            }

            @Override
            public void onFailure(Throwable throwable) {
                // 任务失败
            }
        };
    }
}
