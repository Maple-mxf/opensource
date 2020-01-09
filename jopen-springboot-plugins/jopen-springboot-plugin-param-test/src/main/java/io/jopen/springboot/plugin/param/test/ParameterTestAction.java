package io.jopen.springboot.plugin.param.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;


/**
 * 参数检验
 *
 * @author maxuefeng
 */
@Aspect
@Component
@Order(value = 1)
public class ParameterTestAction   {

    private Logger L = LoggerFactory.getLogger(ParameterTestAction.class);

    private Method getMarkMethod(MethodInvocationProceedingJoinPoint p) {

        // 获取注解
        MethodSignature signature = (MethodSignature) p.getSignature();
        return signature.getMethod();
    }

    /**
     * ProceedingJoinPoint只支持Around
     *
     * @param p
     */
    @Around("@annotation(io.jopen.springboot.plugin.param.test.CheckParamNotNull)")
    public Object around(ProceedingJoinPoint p) throws Throwable {

        if (p instanceof MethodInvocationProceedingJoinPoint) {

            // 获取被注解的方法
            Method method = getMarkMethod((MethodInvocationProceedingJoinPoint) p);

            Object[] args = p.getArgs();

            // 获取方法参数级别的注解
            Parameter[] parameters = method.getParameters();

            // 判断参数级别是否存在NotNull注解
            Optional<Parameter> op = Arrays.stream(parameters).filter(pm -> pm.getAnnotation(NotNull.class) != null).findAny();

            // 存在NotNull注解
            if (op.isPresent()) {

                for (int i = 0; i < parameters.length; i++) {

                    NotNull notNull = parameters[i].getAnnotation(NotNull.class);

                    if (notNull != null) {

                        // 如果需要检验的参数为空
                        if (NotNull.Util.isEmpty(args[i], notNull.strictly(), notNull.requireFields())) {
                            throw new NotNull.NullParamException(String.format("Error Param [ %s ] require not null,Method [ %s ]", parameters[i].getName(), method.getName()));
                        }
                    }

                }
            }

            // 不存在NotNull注解
            else {


                for (int i = 0; i < args.length; i++) {
                    if (NotNull.Util.isEmpty(args[i], false, new String[]{})) {
                        String name = method.getParameters()[i].getName();
                        throw new NotNull.NullParamException(String.format("param [%s] require non null", name));
                    }
                }
            }
        }
        return p.proceed();
    }
}