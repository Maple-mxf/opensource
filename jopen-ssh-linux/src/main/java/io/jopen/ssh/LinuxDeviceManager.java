package io.jopen.ssh;

import ch.ethz.ssh2.Session;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author maxuefeng
 * @see LinuxDevice
 * @since 2020/2/11
 */
public final class LinuxDeviceManager {

    /**
     * 存储device对象
     *
     * @see LinuxDevice
     */
    private final BlockingQueue<LinuxDevice> devices = new PriorityBlockingQueue<>();

    private final SSHSessionPool sessionPool = SSHSessionPool.getInstance();

    private LinuxDeviceManager() {
    }

    /**
     * 单例模式
     */
    public static final LinuxDeviceManager LINUX_DEVICE_MANAGER = new LinuxDeviceManager();


    public void addDevice(@NonNull LinuxDevice device, @NonNull Account account) {
        Preconditions.checkNotNull(device);
        Preconditions.checkNotNull(account);

        synchronized (this) {
            if (this.devices.contains(device)) {
                return;
            }

            // 进行同步认证
            AuthTask authTask = new AuthTask(account, device);
            Response response = authTask.call();
            Session session = (Session) response.getData();

            ListeningSession listeningSession = new ListeningSession(session, account);
            this.sessionPool.add(device, listeningSession);

            // 将当前device放入队列
            this.devices.add(device);
        }
    }

    /**
     * @param task     execute script task {@link java.util.concurrent.Callable}
     * @param callback {@link com.google.common.util.concurrent.FutureCallback}
     * @param <T>      return future type
     * @throws InterruptedException if thread exception
     */
    public <T> void submitTask(@NonNull Task<T> task, LinuxDevice.Callback<T> callback) throws InterruptedException {
        synchronized (this) {
            LinuxDevice device = devices.take();
            device.submitTask(task, callback);
        }
    }


    public void recovery(LinuxDevice device) {
    }

}
