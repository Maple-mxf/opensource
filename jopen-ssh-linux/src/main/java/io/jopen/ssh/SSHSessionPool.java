package io.jopen.ssh;

import com.google.common.collect.MapMaker;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
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
    private final Map<LinuxDevice, List<ListeningSession>> deviceConnectionMeta = new MapMaker()
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

    synchronized void add(@NonNull LinuxDevice device, ListeningSession session) {
        if (this.containDevice(device)) {
            List<ListeningSession> listeningSessions = deviceConnectionMeta.get(device);
            listeningSessions.add(session);
        } else {
            List<ListeningSession> sessionList = new ArrayList<>();
            sessionList.add(session);
            deviceConnectionMeta.put(device, sessionList);
        }

    }

    public final boolean containDevice(LinuxDevice linuxDevice) {
        return deviceConnectionMeta.keySet().stream()
                .anyMatch(d -> d.getAlias().equals(linuxDevice.getAlias()));
    }

    /**
     * @return
     * @see ListeningSession#isUsed()
     */
    public ListeningSession getUsableSession(LinuxDevice device) {
        return this.deviceConnectionMeta.get(device)
                .stream().filter(session -> !session.isUsed()).findAny().orElse(null);
    }

}
