package io.jopen.springboot.plugin.aop;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface ThrowingBiAfterFunction {

    /**
     * @param args
     * @param result 约定大于配置
     * @return
     * @throws RuntimeException
     */
    Object[] accept(Object[] args, Object result) throws Throwable;
}
