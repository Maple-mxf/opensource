package io.jopen.ssh;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.log.Logger;
import com.google.common.base.Preconditions;
import io.jopen.ssh.task.AuthTask;
import io.jopen.ssh.task.FunctionTask;
import io.jopen.ssh.task.ListeningCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author maxuefeng
 * @see LinuxDevice
 * @since 2020/2/11
 */
public final class LinuxDeviceManager {

    private static final Logger LOGGER = Logger.getLogger(LinuxDeviceManager.class);

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

            if (response.isSuccess()) {
                Session session = (Session) response.getData();

                ListeningSession listeningSession = new ListeningSession(device, session, account);
                listeningSession.setUsed(false);

                this.sessionPool.add(device, listeningSession);

                // 将当前device放入队列
                this.devices.add(device);
            } else {
                throw new RuntimeException("auth error");
            }
        }
    }

    /**
     * @param functionTask execute script task {@link FunctionTask}
     * @param callback     {@link com.google.common.util.concurrent.FutureCallback}
     * @param <T>          return future type
     * @throws InterruptedException if thread exception
     * @see ListeningCallable
     */
    public <T> void submitTask(@NonNull FunctionTask<T> functionTask, LinuxDevice.Callback<T> callback) throws InterruptedException {
        LinuxDevice device = devices.take();

        // 申请Session
        ListeningSession usableSession = sessionPool.getUsableSession(device);
        ListeningCallable<T> tListeningCallable = new ListeningCallable<>(usableSession, functionTask);

        device.submitTask(tListeningCallable, callback);
    }


    /**
     * 回收{@link LinuxDevice}
     *
     * @param device {@link LinuxDevice}
     * @throws InterruptedException if thread exception
     */
    void recovery(LinuxDevice device) {
        try {
            int executeTaskNum = device.getExecuteTaskNum().getAndDecrement();
            LOGGER.info(String.format("device executeTaskNum %s", executeTaskNum));
            this.devices.put(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
