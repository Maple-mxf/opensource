package io.jopen.ssh.task;

import ch.ethz.ssh2.Session;
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
    T call(Session session) throws Throwable;

    default T apply(ListeningSession listeningSession) throws Throwable {
        return call(listeningSession.getSession());
    }
}
