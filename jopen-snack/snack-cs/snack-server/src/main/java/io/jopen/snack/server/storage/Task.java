package io.jopen.snack.server.storage;

import com.google.common.util.concurrent.FutureCallback;

import java.util.concurrent.Callable;

/**
 * 当前Task记录刷盘任务
 *
 * @author maxuefeng
 * @see com.google.common.util.concurrent.Service
 * @since 2019/10/23
 */
@Deprecated
public abstract class Task<T> implements Callable<T> {

    @Override
    public abstract T call() throws Exception;

    public abstract FutureCallback<T> completeCallback();
}
