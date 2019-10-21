package io.jopen.distributelock.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * redis的分布式实现基于redis shell command [SETNX key value]
 * <p>{@link Jedis#setnx(String, String)} 1 if the key was set 0 if the key was not set,1代表获取成功，0代表失败 </p>
 *
 * @author maxuefeng
 * @since 2019/10/21
 */
public class JedisDistributeLockImpl {

    private Jedis jedis;

    private final String lockName = "distributeLock";

    @Before
    public void before() {
        jedis = new Jedis("120.55.41.150", 6379);
        jedis.auth("qmbx@@2019");
    }

    /**
     * redis shell command [setnx [key] [value] []]
     */
    @Test
    public void distributeLockShellCommand() {
        // jedis.setnx()
        System.err.println(jedis.setnx(lockName, "value"));
    }

    /**
     * @see SetParams#nx() 不存在时则设置  存在则设置不了值  会返回null
     * @see SetParams#px(long) 设置超时时间  单位是毫秒
     */
    @Test
    public void testGetLock() {
        String lockRes = jedis.set("jedisSetLock", "lockValue", SetParams.setParams().px(5000).nx());
        System.err.println(lockRes);
    }

    @Test
    public void testGetLockLuaScript(){
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    }


}
