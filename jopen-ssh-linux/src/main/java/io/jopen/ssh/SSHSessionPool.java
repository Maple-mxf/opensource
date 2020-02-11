package io.jopen.ssh;

import ch.ethz.ssh2.Session;
import com.google.common.collect.MapMaker;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;

/**
 * SSH连接池
 *
 * @author maxuefeng
 * @since 2020/2/11
 */
final class SSHSessionPool {

    /**
     * Key表示device对象
     */
    private final Map<LinuxDevice, Session> deviceConnectionMeta = new MapMaker()
            .weakKeys().makeMap();

    private static SSHSessionPool instance = null;

    private SSHSessionPool() {
    }

    static synchronized SSHSessionPool getInstance() {
        if (instance == null) {
            instance = new SSHSessionPool();
        }
        return instance;
    }

    void add(@NonNull LinuxDevice device, Session session) {
        deviceConnectionMeta.put(device, session);
    }

}
