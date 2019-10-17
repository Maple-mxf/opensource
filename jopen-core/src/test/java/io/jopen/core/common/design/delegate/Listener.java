package io.jopen.core.common.design.delegate;

import java.util.Arrays;

/**
 * @author maxuefeng
 */
public class Listener {

    public void doSomethings(String... args) {
        Arrays.stream(args).forEach(System.out::println);
    }
}
