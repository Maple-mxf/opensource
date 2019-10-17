package io.jopen.core.common.design.single;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author maxuefeng
 */
public class ExampleSingle {

    /**
     * JDK中自带的单例模式
     */
    @Test
    public void testJdkSimple() throws IOException {

        Runtime runtime = Runtime.getRuntime();

        Process p = runtime.exec("ls");

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;

        AtomicReference<StringBuilder> sb = new AtomicReference<>(new StringBuilder());

        while ((line = reader.readLine()) != null) {
            sb.get().append(line).append("\n");
        }

        String[] rs = sb.get().toString().split("\n");

        Arrays.stream(rs).forEach(System.err::println);

    }
}
