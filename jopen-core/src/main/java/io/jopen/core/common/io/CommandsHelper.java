package io.jopen.core.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author maxuefeng
 * @see ProcessBuilder
 * @see Process
 */
public final class CommandsHelper {
    
    private static String execute(ProcessBuilder processBuilder, boolean async) throws IOException, InterruptedException {

        Process process = processBuilder.start();

        if (!async) process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;

        AtomicReference<StringBuilder> sb = new AtomicReference<>(new StringBuilder());

        while ((line = reader.readLine()) != null) {
            sb.get().append(line).append("\n");
        }

        return sb.get().toString();
    }

    /**
     * 执行单一命令
     *
     * @param command single command
     * @param async   is wait
     * @return return command result line
     * @throws IOException io
     */
    public static String executeCommand(boolean async, String command) throws IOException, InterruptedException {
        return execute(new ProcessBuilder(command), async);
    }

    /**
     * 执行多条命令
     *
     * @param commands one or more command
     * @param async    is wait
     * @return return command result line
     * @throws IOException io
     */
    public static String executeCommands(boolean async, String... commands) throws IOException, InterruptedException {
        return execute(new ProcessBuilder(commands), async);
    }
}
