package io.jopen.core.common.proxology.handlers.early;

import io.jopen.core.common.proxology.handlers.MethodInterpreter;

import java.lang.reflect.Method;

/**
 * @author maxuefeng
 */
@FunctionalInterface
public interface UnboundMethodInterpreter<S> {

    UnboundMethodCallHandler<S> interpret(Method method);

    default MethodInterpreter bind(S state) {
        return method -> interpret(method).bind(state);
    }
}
