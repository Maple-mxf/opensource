package io.jopen.snack.common.listener.table;

import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.event.SnackApplicationEvent;
import io.jopen.snack.common.event.TableEvent;
import io.jopen.snack.common.listener.SnackApplicationListener;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;

/**
 * @author maxuefeng
 * @since 2019/10/27
 */
public class CreateTableEventListener extends SnackApplicationListener {

    @Override
    public void apply(@NonNull SnackApplicationEvent event) {
        // 如果是
        if (event instanceof TableEvent.Create) {

            // 提交创建
            // ListenableFuture<Boolean> future = service.submit(new PersistenceTableInfoTask());
            // 添加回调函数
            // Futures.addCallback(future, new Callback(), super.service);
            submit(new PersistenceTableInfoTask(), new Callback());
        }
    }

    class PersistenceTableInfoTask implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            return null;
        }
    }

    private final class Callback implements FutureCallback<Boolean> {

        @Override
        public void onSuccess(@Nullable Boolean result) {

        }

        @Override
        public void onFailure(Throwable throwable) {

        }
    }
}
