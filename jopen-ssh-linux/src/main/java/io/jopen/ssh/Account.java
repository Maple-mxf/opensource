package io.jopen.ssh;

import java.io.File;

/**
 * 账户对象和LinuxDevice {@link LinuxDevice}属于多对一的关系
 *
 * @author maxuefeng
 * @since 2020/2/11
 */
public class Account implements java.io.Serializable {

    public static final String ROOT_ACCOUNT = "root";

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
     *
     */
    private boolean isRoot;


    private boolean available = true;

    /**
     * 账户登录方式
     */
    private LoginType loginType = LoginType.PASSWORD;

    enum LoginType {
        /**
         * 密码登录
         */
        PASSWORD,

        /**
         * 密钥登录
         */
        SECRET
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getSecret() {
        return secret;
    }

    public void setSecret(File secret) {
        this.secret = secret;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
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

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
