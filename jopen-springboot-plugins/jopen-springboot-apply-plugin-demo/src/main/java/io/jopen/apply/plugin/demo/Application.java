package io.jopen.apply.plugin.demo;

import io.jopen.apply.plugin.demo.mongo.UserRepository;
import io.jopen.springboot.plugin.annotation.cache.EnableJopenAnnotationCache;
import io.jopen.springboot.plugin.encryption.annotation.EnableJopenEncryptBody;
import io.jopen.springboot.plugin.init.EnableJopenInit;
import io.jopen.springboot.plugin.limit.EnableJopenLimit;
import io.jopen.springboot.plugin.param.test.EnableJopenParamTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.Map;

/**
 * 以下为注解式插件
 * 基于redis的限流{@link EnableJopenLimit} 导入这个注解则会将插件导入Spring容器，但是需要本项目配置Redis，详情请看
 * {@link io.jopen.apply.plugin.demo.config.WebConfig#redisTemplate(LettuceConnectionFactory)}
 * <p>
 * 基于加密解密插件{@link EnableJopenEncryptBody} 导入这个注解即开启了加密解密，也需要在yml文件中配置秘钥，详情请看
 * application.yml文件，具体使用请查看{@link io.jopen.apply.plugin.demo.controller.LoginApi#login(Map)}
 * <p>
 * 基于注解扫描的初始化插件{@link EnableJopenInit}  只需要初始化的类上面添加{@link io.jopen.springboot.plugin.init.Init}
 * 注解，即模块化的初始工作  具体使用方式请查看{@link io.jopen.apply.plugin.demo.init.InitDemo}
 * <p>
 * 基于AOP的参数检验{@link EnableJopenParamTest} 只需要在方法上添加{@link io.jopen.springboot.plugin.param.test.CheckParamNotNull}
 * 此注解会严格检验各个参数，如果需要部分参数不为null,则需要和{@link io.jopen.springboot.plugin.param.test.NotNull}注解配合使用,
 * 具体使用方式请查看{@link io.jopen.apply.plugin.demo.controller.LoginApi#login(String, String)}
 * {@link io.jopen.apply.plugin.demo.controller.LoginApi#login(Map)}
 * <p>
 * 基于Fluent风格的mongo构建器{@link io.jopen.springboot.plugin.mongo.template.builder.Builder},同时也补充了SpringBoot Mongo
 * Template的不足之处。详情请看{@link io.jopen.springboot.plugin.mongo.template.builder.IQuery},具体使用方式请参考
 * {@link UserRepository#getAggUserList()} {@link UserRepository#getUserListByExcludeSomeFields()}
 * <p>
 * 基于更高级Spring AOP的封装，详情请查看{@link io.jopen.springboot.plugin.aop.AbstractAopAction},面向Lambda编程范式，是的开发人员只关心业务即可
 * 无需关心代码的执行顺序，目的是使用层面仅仅面向定义而已，其他具体怎样执行无需关心。将程序的阶分的更清楚更细致,具体使用请看
 * {@link io.jopen.apply.plugin.demo.aop.LoginAopDemo}
 *
 * @author maxuefeng
 * @since 2020/1/9
 */
@SpringBootApplication

/*注解式插件*/
@EnableJopenLimit
@EnableJopenEncryptBody
@EnableJopenInit
@EnableJopenParamTest
@EnableJopenAnnotationCache
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
