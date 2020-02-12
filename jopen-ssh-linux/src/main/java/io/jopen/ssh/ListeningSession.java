package io.jopen.ssh;

import ch.ethz.ssh2.Session;

/**
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
