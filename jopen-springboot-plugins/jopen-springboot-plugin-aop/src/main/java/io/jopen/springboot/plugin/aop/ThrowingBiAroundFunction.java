package io.jopen.springboot.plugin.aop;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface ThrowingBiAroundFunction {

    /**
     * @param args
     * @param result 约定大于配置
     * @return
     * @throws RuntimeException
     */
    ReturnValue accept(Object[] args, Object result) throws Throwable;
}
