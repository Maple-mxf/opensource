package io.jopen.ssh;

import com.google.common.base.Preconditions;

import java.io.File;

/**
 * 账户对象和LinuxDevice {@link LinuxDevice}属于多对一的关系
 *
 * @author maxuefeng
 * @since 2020/2/11
 */
public class Account implements java.io.Serializable {

    static final String ROOT_ACCOUNT = "root";

    /**
     * 默认SSH端口
     */
    private static final int DEFAULT_SSH_PORT = 22;


    /**
     * 登录用户名
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     * 密钥
     */
    private File secret;

    /**
     * 是否属于root用户
     */
    private boolean isRoot;

    /**
     * 是否可用
     */
    private boolean available = true;

    /**
     * 连接port
     */
    private int port = DEFAULT_SSH_PORT;

    /**
     * 账户登录方式
     */
    private LoginType loginType;

    public enum LoginType {
        /**
         * 密码登录
         */
        PASSWORD,

        /**
         * 密钥登录
         */
        SECRET
    }

    public Account(String username, String password, File secret, LoginType loginType) {

        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        Preconditions.checkNotNull(loginType);

        this.username = username;
        this.password = password;
        this.loginType = loginType;

        if (LoginType.SECRET.equals(loginType)) {
            Preconditions.checkNotNull(secret);
            this.secret = secret;
        }
        isRoot = ROOT_ACCOUNT.equals(username);
    }

    public Account(String username, String password, File secret, LoginType loginType, int port) {

        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        Preconditions.checkNotNull(loginType);

        this.username = username;
        this.password = password;
        this.loginType = loginType;

        if (LoginType.SECRET.equals(loginType)) {
            Preconditions.checkNotNull(secret);
            this.secret = secret;
        }
        this.isRoot = ROOT_ACCOUNT.equals(username);
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public File getSecret() {
        return secret;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public int getPort() {
        return port;
    }
}
