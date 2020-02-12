package io.jopen.ssh;

import ch.ethz.ssh2.Session;

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
     * @see Session
     */
    private Session session;

    public ListeningSession(Session session, Account account) {
        this.currentAccount = account;
        this.session = session;
        this.isRoot = Account.ROOT_ACCOUNT.equals(account.getUsername());
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
