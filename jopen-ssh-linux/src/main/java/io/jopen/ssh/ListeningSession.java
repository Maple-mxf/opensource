package io.jopen.ssh;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.locks.ReentrantLock;

/**
 * A <code>Session</code> is a remote execution of a program. "Program" means
 * in this context either a shell, an application or a system command. The
 * program may or may not have a tty. Only one single program can be started on
 * a session. However, multiple sessions can be active simultaneously.
 * <p>
 * <p>
 * {@link Session} can not concurrent,but one device build multi {@link Session}
 *
 * @author maxuefeng
 * @since 2020/2/12
 */
public class ListeningSession implements java.io.Serializable {

    /**
     * 当前Session使用的Account
     *
     * @see Account
     */
    private Account currentAccount;

    /**
     * 当前登录账号是否是超级管理员
     */
    private boolean isRoot;

    /**
     * 对应的{@link LinuxDevice}
     */
    private LinuxDevice device;

    /**
     * @see Session
     */
    private Session session;

    /**
     * 当前{@link Session}是否在使用
     */
    private boolean used;

    /**
     * 当前{@link Session}对应的连接
     */
    private ch.ethz.ssh2.Connection connection;


    /**
     * 当前{@link Session} 对应的 connection info
     *
     * @see ConnectionInfo
     */
    private ConnectionInfo connectionInfo;

    /**
     * fair lock
     */
    private ReentrantLock usedLock = new ReentrantLock(true);

    public ListeningSession(LinuxDevice device, @Nullable Session session, Account account) {
        this.device = device;
        this.currentAccount = account;
        this.session = session;
        this.isRoot = Account.ROOT_ACCOUNT.equals(account.getUsername());
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Session getSession() {
        return session;
    }

    public void setUsed(boolean used) {
        this.usedLock.lock();
        this.used = used;
        this.usedLock.unlock();
    }

    public boolean isUsed() {
        return this.used;
    }

    public LinuxDevice getDevice() {
        return this.device;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    /**
     * TODO  当前机器的连接状态
     */
    enum SessionConnectState {
        CONNECTED,
        NO_CONNECT,
        BROKEN
    }
}
