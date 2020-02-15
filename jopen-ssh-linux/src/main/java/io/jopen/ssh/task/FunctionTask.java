package io.jopen.ssh.task;

import io.jopen.ssh.ListeningSession;

/**
 * @author maxuefeng
 * @since 2020/2/12
 */
@FunctionalInterface
public interface FunctionTask<T> {

    /**
     * @param session {@link ListeningSession}
     * @return T
     */
    T applyTask(ListeningSession session) throws Throwable;
}
