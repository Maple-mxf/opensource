package io.jopen.springboot.plugin.aop;


/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface ThrowingBeforeFunction {

    /**
     * @param args 加工参数
     * @return
     * @throws RuntimeException
     */
    Object[] accept(Object[] args) throws Throwable;
}
