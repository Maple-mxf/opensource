package io.jopen.snack.common.task;

import com.google.common.util.concurrent.FutureCallback;
import io.jopen.snack.common.event.SnackApplicationEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author maxuefeng
 * @since 2019/10/28
 */
public abstract class PersistenceTask<T> implements Callable<T> {

    protected Runnable taskExecuteListener;
    protected FutureCallback<T> futureCallback;
    protected SnackApplicationEvent event;

    //
    protected PersistenceTask(@Nullable Runnable taskExecuteListener,
                              @NonNull FutureCallback<T> futureCallback,
                              @NonNull SnackApplicationEvent event) {
        this.taskExecuteListener = taskExecuteListener;
        this.futureCallback = futureCallback;
        this.event = event;
    }

    @Override
    public T call() throws Exception {
        return execute();
    }

    public abstract T execute() throws IOException;

    public Runnable getTaskExecuteListener() {
        return taskExecuteListener;
    }

    public void setTaskExecuteListener(Runnable taskExecuteListener) {
        this.taskExecuteListener = taskExecuteListener;
    }

    public FutureCallback<T> getFutureCallback() {
        return futureCallback;
    }

    public void setFutureCallback(FutureCallback<T> futureCallback) {
        this.futureCallback = futureCallback;
    }

    public SnackApplicationEvent getEvent() {
        return event;
    }

    public void setEvent(SnackApplicationEvent event) {
        this.event = event;
    }
}
