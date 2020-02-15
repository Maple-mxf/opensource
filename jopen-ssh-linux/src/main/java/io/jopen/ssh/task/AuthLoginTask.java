package io.jopen.ssh.task;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.google.common.base.Preconditions;
import io.jopen.ssh.Account;
import io.jopen.ssh.LinuxDevice;
import io.jopen.ssh.ListeningSession;
import io.jopen.ssh.Response;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author maxuefeng
 * @since 2020/2/11
 */
public final class AuthLoginTask implements Callable<Response> {

    /**
     * 账户
     */
    private Account account;

    /**
     * 分配的Linux机器{@link LinuxDevice}
     */
    private LinuxDevice linuxDevice;

    /**
     * @see Session
     */
    private ListeningSession listeningSession;

    public AuthLoginTask(Account account, LinuxDevice linuxDevice, ListeningSession listeningSession) {

        Preconditions.checkNotNull(account);
        Preconditions.checkNotNull(linuxDevice);
        Preconditions.checkNotNull(listeningSession);
        this.account = account;
        if (account.getLoginType().equals(Account.LoginType.SECRET)
                && account.getSecret() == null) {
            throw new RuntimeException("证书参数不可为空");
        }
        this.linuxDevice = linuxDevice;
        this.listeningSession = listeningSession;
    }

    @Override
    public Response call() {
        try {
            Connection connection = listeningSession.getConnection();
            Session session = login(connection, account);
            return Response.ok(session);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    /**
     * 执行登录任务
     */
    private Session login(Connection connection, Account account) throws IOException {
        boolean authenticateSuccess;
        if (account.getLoginType().equals(Account.LoginType.PASSWORD)) {
            authenticateSuccess = connection.authenticateWithPassword(account.getUsername(), account.getPassword());
        } else if (account.getLoginType().equals(Account.LoginType.SECRET)) {
            authenticateSuccess = connection.authenticateWithPublicKey(account.getUsername(), account.getSecret(), account.getPassword());
        } else
            throw new RuntimeException("unsupport login type");

        if (!authenticateSuccess)
            throw new RuntimeException("auth failed,please check your account and password");
        return connection.openSession();
    }
}
