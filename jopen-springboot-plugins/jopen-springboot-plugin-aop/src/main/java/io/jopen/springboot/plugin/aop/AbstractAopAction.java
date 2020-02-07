package io.jopen.springboot.plugin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.LinkedHashMap;

/**
 * 业务级别的Aop
 *
 * @author maxuefeng
 * @since 2019-05-02
 */
public abstract class AbstractAopAction {

    protected AbstractAopAction() {
    }

    // K为要执行的函数  V为处理函数执行的结果
    protected LinkedHashMap<ThrowingBeforeFunction, ResultHandler> beforeActions = new LinkedHashMap<>();

    // afterActions
    protected LinkedHashMap<ThrowingBiAfterFunction, ResultHandler> afterActions = new LinkedHashMap<>();

    /**
     * 定义切面
     */
    public abstract void pointCut();


    protected void doBefore(Object[] args) throws Throwable {
        for (ThrowingBeforeFunction action : beforeActions.keySet()) {
            Object[] accept = action.accept(args);
            beforeActions.get(action).handler(accept);
        }
    }

    protected void doAfter(Object[] args, Object result) throws Throwable {

        for (ThrowingBiAfterFunction action : afterActions.keySet()) {
            Object[] accept = action.accept(args, result);
            afterActions.get(action).handler(accept);
        }
    }

    /**
     * Aop操作分为三种  1 进入目标方法之前  2  执行完方法之后   3  环绕执行
     *
     * @param p
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint p) throws Throwable {

        // 登录之前操作
        Object[] args = p.getArgs();

        // 此处的异常处理由业务需求而定   如果不需要往外抛出
        // 则消化在ThrowingBeforeFunction函数体中即可，如果需要往外抛出异常此处默认不处理异常
        doBefore(args);

        // 登录之后操作  在执行目标方法的时候可能会出现异常  此处不捕获异常   在外层ControllerAdvice进行处理
        Object result = p.proceed();

        doAfter(args, result);

        return result;
    }
}
