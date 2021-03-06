package io.jopen.core.function;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface ResultHandler {

    /**
     * 忽略结果处理
     *
     * @return
     */
    static ResultHandler ignore() {
        return returnValue -> {
        };
    }

    void handler(ReturnValue returnValue);
}
