package core.common.proxology.handlers;


/**
 * 方法反射调用
 *
 * @author maxuefeng
 */
@FunctionalInterface
public interface MethodCallHandler {

    /**
     * 反射调用
     *
     * @param proxy
     * @param args
     * @return
     * @throws Throwable
     */
    Object invoke(Object proxy, Object[] args) throws Throwable;
}
