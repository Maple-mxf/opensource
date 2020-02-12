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

    public static final LinuxDeviceManager LINUX_DEVICE_MANAGER = new LinuxDeviceManager();


    public synchronized void addDevice(@NonNull LinuxDevice device, @NonNull Account account) {
        Preconditions.checkNotNull(device);
        Preconditions.checkNotNull(account);

        if (this.devices.contains(device)) {
            return;
        }

        // 进行同步认证
        AuthTask authTask = new AuthTask(account, device);
        Response response = authTask.call();
        Session session = (Session) response.getData();
        //
        LinuxDevice linuxDevice = sessionPool.containDevice(device);
        if (linuxDevice != null) {
            sessionPool.g
        }

        sessionPool.add(device, session);

        // 将当前device放入队列
        this.devices.add(device);
    }

    public <T> void submitTask(@NonNull Task<T> task, LinuxDevice.Callback<T> callback) throws InterruptedException {
        synchronized (this) {
            LinuxDevice device = devices.take();
            device.submitTask(task, callback);
        }
    }

}
