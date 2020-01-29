package io.jopen.springboot.plugin.limit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author maxuefeng
 * @since 2020/1/29
 */
@Configuration
public class ScriptConfiguration {

    /**
     * 加载Lua限流脚本
     *
     * @return 脚本包装对象
     */
    @Bean(name = "limitScript")
    public DefaultRedisScript<Number> limitScript() {

        DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Number.class);
        // redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/limiting.lua")));
        String limitLuaScript = "local key = \"rate.limit:\" .. KEYS[1]\n" +
                "local limit = tonumber(ARGV[1])\n" +
                "local current = tonumber(redis.call('get', key) or \"0\")\n" +
                "if current + 1 > limit then\n" +
                "    return 0\n" +
                "else\n" +
                "    redis.call(\"INCRBY\", key, \"1\")\n" +
                "    redis.call(\"expire\", key, ARGV[2])\n" +
                "    return current + 1\n" +
                "end";
        redisScript.setScriptText(limitLuaScript);
        return redisScript;
    }
}
