package core.function.consumer;

/**
 * @author maxuefeng
 * @since 2019-04-26
 */
@FunctionalInterface
public interface BiThrowingConsumer<T,U> {

    /**
     * 无需出参 如果数据检测到数据异常  直接抛出异常
     *
     * @param t
     * @throws Throwable
     */
    void accept(T t, U u) throws Throwable;
}
