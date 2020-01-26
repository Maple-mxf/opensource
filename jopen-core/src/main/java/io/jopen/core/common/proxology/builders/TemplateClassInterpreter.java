package io.jopen.core.common.proxology.builders;

import io.jopen.core.common.proxology.arguments.ArgumentConversion;
import io.jopen.core.common.proxology.handlers.early.ClassInterpreter;
import io.jopen.core.common.proxology.handlers.early.UnboundMethodCallHandler;
import io.jopen.core.common.proxology.handlers.early.UnboundMethodInterpreter;
import io.jopen.core.common.proxology.reflection.MethodInfo;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author maxuefeng
 */
public final class TemplateClassInterpreter {


    /**
     *
     */
    private static final ClassInterpreter<Map<String, Object>> cache =
            ClassInterpreter.cached(
                    ClassInterpreter.mappingWith(TemplateClassInterpreter::interpret));

    /**
     * @param templateClass
     * @return
     */
    public static UnboundMethodInterpreter<Map<String, Object>> interpret(Class<?> templateClass) {
        return cache.interpret(templateClass);
    }

    /**
     * @param method
     * @return
     */
    private static UnboundMethodCallHandler<Map<String, Object>> interpret(Method method) {
        MethodInfo methodInfo = MethodInfo.forMethod(method);
        String propertyName = methodInfo.getPropertyName();
        return state -> (proxy, args) -> ArgumentConversion.convert(
                methodInfo.getReturnType(),
                state.getOrDefault(propertyName, args[0]));
    }

}
