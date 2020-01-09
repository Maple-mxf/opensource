package io.jopen.apply.plugin.demo.aop;

import io.jopen.springboot.plugin.aop.*;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * {@link ResultHandler}
 * {@link ReturnValue}
 *
 * @author maxuefeng
 * @since 2020/1/9
 */
@Component
@Aspect
public class LoginAopDemo extends AbstractAopAction {


    /*切面位置定义*/
    @Override
    @Pointcut("execution(public * io.jopen.apply.plugin.demo.controller.LoginApi.login(..))")
    public void pointCut() {
    }

    // 无参数构造方法
    public LoginAopDemo() {

        // 登录前操作  此Action可以定义多个，只需要按照顺序排列存放即可
        this.beforeActions.put(this.checkAccount, ResultHandler.ignore());

        // 登录后操作
        this.afterActions.put(this.initRewardInfo, ResultHandler.ignore());
    }

    // 登录之后先判断用户的账户是否正常
    private ThrowingBeforeFunction checkAccount = (args) -> {
        // 获取控制器入参
        Object[] params = args;
        // TODO 检测逻辑
        return ReturnValue.empty();
    };

    /*登录成功后初始化登录奖励信息*/
    private ThrowingBiAfterFunction initRewardInfo = (args, result) -> {
        Map<String, Object> loginRet = (Map<String, Object>) result;
        if (loginRet.get("success").equals(Boolean.TRUE)) {
            // TODO  记录操作
        }
        return ReturnValue.empty();
    };
}
