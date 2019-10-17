package io.jopen.core.common;

import java.util.Arrays;

/**
 * @author maxuefeng
 */
public class Include {

    private Object object;

    public Include(Object object) {
        this.object = object;
    }

    public static Include build(Object object) {
        return new Include(object);
    }

    public boolean in(Object... objs) {
        return Arrays.asList(objs).contains(this.object);
    }

    public static void main(String[] args) {
    }
}
