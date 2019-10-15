package core.function;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface StreamCheckedFunction<T, R> {

    /**
     * 检测异常   在钩子函数中消化异常
     *
     * @param t
     * @return
     * @throws Exception
     */
    R apply(T t) throws Exception;
}
