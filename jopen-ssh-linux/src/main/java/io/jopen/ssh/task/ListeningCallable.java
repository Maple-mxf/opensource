package io.jopen.ssh.task;

import io.jopen.ssh.ListeningSession;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Callable;


/**
 * 需要{@link ch.ethz.ssh2.Session}的任务
 *
 * @author maxuefeng
 * @since 2020/2/11
 */
public class ListeningCallable<T> implements Callable<T> {

    private ListeningSession listeningSession;
    private FunctionTask<T> functionTask;

    public ListeningCallable(@NonNull ListeningSession listeningSession, @NonNull FunctionTask<T> functionTask) {
        this.listeningSession = listeningSession;
        this.functionTask = functionTask;
    }

    /**
     * 不管出不出异常  必须要释放session
     *
     * @return
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        try {
            T result = functionTask.applyTask(this.listeningSession);
            this.listeningSession.setUsed(true);
            return result;
        } catch (Throwable t) {
            this.listeningSession.setUsed(true);
            throw new RuntimeException(t.getMessage());
        }
    }
}
