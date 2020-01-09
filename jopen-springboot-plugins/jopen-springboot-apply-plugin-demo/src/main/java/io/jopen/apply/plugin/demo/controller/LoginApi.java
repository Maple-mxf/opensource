package io.jopen.apply.plugin.demo.controller;

import com.google.common.collect.ImmutableMap;
import io.jopen.springboot.plugin.encryption.annotation.decrypt.DESDecryptBody;
import io.jopen.springboot.plugin.encryption.annotation.encrypt.DESEncryptBody;
import io.jopen.springboot.plugin.limit.Limiting;
import io.jopen.springboot.plugin.param.test.CheckParamNotNull;
import io.jopen.springboot.plugin.param.test.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author maxuefeng
 * @since 2020/1/9
 */
@RestController
@RequestMapping(value = "/api/login")
public class LoginApi {

    @RequestMapping(value = "")
    @Limiting  // 进行限流   只对当前方法限流，如果加到控制器则对于当前控制器下的所有接口进行限流
    @DESDecryptBody  // 对Http请求体进行解密
    @DESEncryptBody  // 对于返回参数进行加密
    @CheckParamNotNull // 检验参数不可为空
    public Map<String, Object> login(@RequestBody Map<String, Object> body) {
        return ImmutableMap.of();
    }


    /**
     * 参数检验注解
     *
     * @param username
     * @param password
     */
    @CheckParamNotNull
    private void login(@NotNull String username, String password) {
        if (!(username.equals("admin") && password.equals("123"))) {
            throw new RuntimeException("登录失败");
        }
    }

}
