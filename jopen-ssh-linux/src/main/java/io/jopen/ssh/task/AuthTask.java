package io.jopen.ssh.task;

import ch.ethz.ssh2.Session;
import io.jopen.ssh.Account;
import io.jopen.ssh.LinuxDevice;
import io.jopen.ssh.Response;

import java.util.concurrent.Callable;

/**
 * @author maxuefeng
 * @since 2020/2/11
 */
public final class AuthTask implements Callable<Response> {

    /**
     * 账户
     */
    private Account account;

    /**
     * 分配的Linux机器{@link LinuxDevice}
     */
    private LinuxDevice linuxDevice;

    public AuthTask(Account account, LinuxDevice linuxDevice) {
        this.account = account;
        if (account.getLoginType().equals(Account.LoginType.SECRET)
                && account.getSecret() == null) {
            throw new RuntimeException("证书参数不可为空");
        }
        this.linuxDevice = linuxDevice;
    }

    @Override
    public Response call() {
        try {
            Session session = linuxDevice.login(account);
            return Response.ok(session);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}
