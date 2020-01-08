package io.jopen.springboot.plugin.aop;


/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface ThrowingBeforeFunction {

    /**
     *
     * @param args
     * @return
     * @throws RuntimeException
     */
    ReturnValue accept(Object[] args) throws Throwable;
}
