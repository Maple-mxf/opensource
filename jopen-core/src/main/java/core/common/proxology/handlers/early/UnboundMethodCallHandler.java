package core.common.proxology.handlers.early;


import io.jopen.core.common.proxology.handlers.MethodCallHandler;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface UnboundMethodCallHandler<S> {
    MethodCallHandler bind(S state);
}
