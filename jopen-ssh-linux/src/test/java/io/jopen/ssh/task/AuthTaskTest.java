package io.jopen.ssh.task;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import io.jopen.ssh.Account;
import io.jopen.ssh.LinuxDevice;
import io.jopen.ssh.LinuxDeviceManager;
import io.jopen.ssh.ListeningSession;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * @author maxuefeng
 * @since 2020/2/15
 */
public class AuthTaskTest {

    LinuxDeviceManager deviceManager = LinuxDeviceManager.LINUX_DEVICE_MANAGER;

    @Before
    public void addDevice() throws IOException {

        LinuxDevice device = new LinuxDevice("jd-1", "114.67.246.62", 22);
        File secretFile = new File("E:\\java-workplace\\opensource\\jopen-ssh-linux\\src\\test\\java\\io\\jopen\\ssh\\task\\common_secret.pem");
        Account account = new Account("root", "qmbx@@2019", secretFile, Account.LoginType.SECRET);
        deviceManager.addDevice(device, account);
    }

    class LSTask implements FunctionTask<Object> {
        @Override
        public Object applyTask(ListeningSession listeningSession) throws Throwable {

            Session session = listeningSession.getSession();

            session.requestPTY("bash");
            session.startShell();


            InputStream stdOut = new StreamGobbler(session.getStdout());
            InputStream stdErr = new StreamGobbler(session.getStderr());

            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdOut));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stdErr));

            // 准备输入命令
            PrintWriter out = new PrintWriter(session.getStdin());

            // 输入待执行命令
            out.println("ls /");
            out.println("exit");

            // 关闭输入流
            out.close();

            // 等待，除非1.连接关闭；2.输出数据传送完毕；3.进程状态为退出；4.超时
            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 30000);

            System.out.println("Here is the output from stdout:");

            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            System.out.println("Here is the output from stderr:");
            while (true) {
                String line = stderrReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            System.out.println("ExitCode: " + session.getExitStatus());
            Integer exitStatus = session.getExitStatus();
            System.err.println(exitStatus);

            return null;
        }
    }


    @Test
    public void testLSCommand() throws InterruptedException {
        deviceManager.submitTask(new LSTask(), new LinuxDevice.Callback<Object>() {
            @Override
            protected void completedOnSuccess(@Nullable Object result) {
                System.err.println(result);
            }

            @Override
            protected void completedOnFailure(Throwable t) {
                System.err.println(t);
            }
        });

        Thread.sleep(100000);
    }


}
