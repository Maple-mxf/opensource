package io.jopen.springboot.plugin.auth;

import com.google.common.base.Verify;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
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
     * 凭证是否有效
     */
    private boolean valid = true;

    /**
     * 用户角色信息
     */
    private String[] roles;

    /**
     * 用户信息缓存
     */
    private Object userInfo;


    /**
     *
     */
    private boolean empty;

    private Credential(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean getValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Serializable getIdentity() {
        return identity;
    }

    public void setIdentity(Serializable identity) {
        this.identity = identity;
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

    public static class Builder {
        private Credential credential;

        public Builder(boolean isEmpty) {
            this.credential = new Credential(isEmpty);
        }

        public Builder identity(@NonNull Serializable identity) {
            this.credential.setIdentity(identity);
            return this;
        }

        public Builder roles(@NonNull String... roles) {
            this.credential.setRoles(roles);
            return this;
        }

        public Builder valid(boolean valid) {
            this.credential.valid = valid;
            return this;
        }

        public Builder userInfo(Object userInfo) {
            this.credential.setUserInfo(userInfo);
            return this;
        }

        public Credential build() {
            if (!this.credential.empty) {
                Verify.verify(this.credential.roles != null, "user must has an roles");
            }
            return this.credential;
        }
    }
}
