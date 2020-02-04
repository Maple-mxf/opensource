package io.jopen.springboot.plugin.auth;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * 用户身份凭证
 *
 * @author maxuefeng
 * @see Function#identity()
 * @since 2020/2/4
 */
public final class Credential implements java.io.Serializable {

    /**
     * 用户身份的唯一标识符
     */
    private Serializable identity;

    /**
     * 账户状态
     */
    private AccountState accountState;

    /**
     * 用户角色信息
     */
    private String[] roles;

    /**
     * 用户信息缓存
     */
    private Object userInfo;

    private boolean empty;

    public Credential(boolean empty) {
        this.empty = empty;
    }

    public enum AccountState {
        /**
         * 冻结状态
         */
        FREEZE,
        /**
         * 正常状态
         */
        NORMAL,
        /**
         * 危险状态
         */
        DANGER
    }

    public Serializable getIdentity() {
        return identity;
    }

    public void setIdentity(Serializable identity) {
        this.identity = identity;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public void setAccountState(AccountState accountState) {
        this.accountState = accountState;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(identity, that.identity) &&
                accountState == that.accountState &&
                Arrays.equals(roles, that.roles) &&
                Objects.equals(userInfo, that.userInfo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(identity, accountState, userInfo);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "identity=" + identity +
                ", accountState=" + accountState +
                ", roles=" + Arrays.toString(roles) +
                ", userInfo=" + userInfo +
                '}';
    }
}
