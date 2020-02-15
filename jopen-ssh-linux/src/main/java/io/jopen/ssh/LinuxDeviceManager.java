package io.jopen.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.log.Logger;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Verify;
import com.google.common.collect.MapMaker;
import com.google.common.util.concurrent.FutureCallback;
import io.jopen.ssh.task.AuthLoginTask;
import io.jopen.ssh.task.FunctionTask;
import io.jopen.ssh.task.ListeningCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.*;
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

    /**
     * manager是否启动
     */
    private volatile boolean started = false;

    /**
     * 为开发者提供的设备信息加载模块
     *
     * @see Supplier
     */
    @NonNull
    private Supplier<Map<LinuxDevice, Set<Account>>> linuxDeviceSupplier;

    /**
     * Key表示device对象
     */
    private final Map<LinuxDevice, List<ListeningSession>> deviceConnectionMeta = new MapMaker()
            .weakKeys().makeMap();

    private static LinuxDeviceManager instance = null;

    private LinuxDeviceManager(@NonNull Supplier<Map<LinuxDevice, Set<Account>>> linuxDeviceSupplier) {
        Preconditions.checkNotNull(linuxDeviceSupplier);
        this.linuxDeviceSupplier = linuxDeviceSupplier;
    }

    public static synchronized LinuxDeviceManager getInstance(Supplier<Map<LinuxDevice, Set<Account>>> linuxDeviceFunction) {
        if (null == instance) {
            instance = new LinuxDeviceManager(linuxDeviceFunction);
        }
        return instance;
    }

    /**
     * 初始化Linux设备的信息
     *
     * @see Supplier
     */
    public synchronized void start() {
        Verify.verify(this.started, "Linux Manager was started");
        Map<LinuxDevice, Set<Account>> deviceAccounts = this.linuxDeviceSupplier.get();
        Preconditions.checkNotNull(deviceAccounts);

        deviceAccounts.forEach((device, accountSet) ->
                accountSet.forEach(account -> {
                    try {
                        addLinuxDevice(device, account);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage());
                    }
                }));
        this.started = true;
    }

    /**
     * 动态添加设备  开发者可自行添加设备
     */
    public synchronized void addLinuxDevice(@NonNull LinuxDevice device, @NonNull Account account) throws IOException {
        Preconditions.checkNotNull(device);
        Preconditions.checkNotNull(account);

        synchronized (this) {
            if (this.devices.contains(device)) return;

            // 进行同步认证
            ListeningSession listeningSession = new ListeningSession(device, null, account);
            Connection connection = new Connection(device.getIp(), account.getPort());
            listeningSession.setConnection(connection);
            ConnectionInfo connectionInfo = connection.connect();
            listeningSession.setConnectionInfo(connectionInfo);

            AuthLoginTask authLoginTask = new AuthLoginTask(account, device, listeningSession);
            Response response = authLoginTask.call();

            if (response.isSuccess()) {
                Session session = (Session) response.getData();
                session.requestPTY("bash");
                session.startShell();
                listeningSession.setUsed(false);
                this.putSession(device, listeningSession);
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
    public <T> void submitTask(@NonNull FunctionTask<T> functionTask, FutureCallback<T> callback) throws InterruptedException {
        LinuxDevice device = devices.take();

        // 申请Session
        ListeningSession usableSession = this.getUsableSession(device);
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

    synchronized void putSession(@NonNull LinuxDevice device, ListeningSession session) {
        if (this.containDevice(device)) {
            List<ListeningSession> listeningSessions = deviceConnectionMeta.get(device);
            listeningSessions.add(session);
        } else {
            List<ListeningSession> sessionList = new ArrayList<>();
            sessionList.add(session);
            deviceConnectionMeta.put(device, sessionList);
        }

    }

    private boolean containDevice(LinuxDevice linuxDevice) {
        return deviceConnectionMeta.keySet().stream()
                .anyMatch(d -> d.getAlias().equals(linuxDevice.getAlias()));
    }

    /**
     * @see ListeningSession#isUsed()
     */
    ListeningSession getUsableSession(LinuxDevice device) {
        return this.deviceConnectionMeta.get(device)
                .stream().filter(session -> !session.isUsed()).findAny().orElse(null);
    }
}
