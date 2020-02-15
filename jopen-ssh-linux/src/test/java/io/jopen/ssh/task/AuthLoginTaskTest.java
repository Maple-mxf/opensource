package io.jopen.ssh.task;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.google.common.util.concurrent.FutureCallback;
import io.jopen.ssh.Account;
import io.jopen.ssh.LinuxDevice;
import io.jopen.ssh.LinuxDeviceManager;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * @author maxuefeng
 * @since 2020/2/15
 */
public class AuthLoginTaskTest {

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
        public Object call(Session session) throws Throwable {

            InputStream stdOut = new StreamGobbler(session.getStdout());
            InputStream stdErr = new StreamGobbler(session.getStderr());

            // 读取正确的bash响应
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdOut));
            // 读取错误的bash响应
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stdErr));

            PrintWriter out = new PrintWriter(session.getStdin());

            // 输入待执行命令
            out.println("ls /");

            // 关闭输入流
            out.close();

            // 等待，除非1.连接关闭；2.输出数据传送完毕；3.进程状态为退出；4.超时
            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 30000);

            while (true) {
                String line = stdoutReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            while (true) {
                String line = stderrReader.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }

            return null;
        }
    }


    @Test
    public void testLSCommand() throws InterruptedException {
        deviceManager.submitTask(new LSTask(),new FutureCallback<Object>(){
            @Override
            public void onSuccess(@Nullable Object result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        Thread.sleep(100000);
    }


}
