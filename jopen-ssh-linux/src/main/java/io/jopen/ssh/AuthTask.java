package io.jopen.ssh;

import ch.ethz.ssh2.Session;

/**
 * @author maxuefeng
 * @since 2020/2/11
 */
public final class AuthTask extends Task<Response> {

    /**
     * 账户
     */
    private Account account;

    /**
     * 分配的Linux机器{@link LinuxDevice}
     */
    private LinuxDevice linuxDevice;

    AuthTask(Account account, LinuxDevice linuxDevice) {
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
