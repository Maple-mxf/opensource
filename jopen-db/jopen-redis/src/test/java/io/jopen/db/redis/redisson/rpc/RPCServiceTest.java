package io.jopen.db.redis.redisson.rpc;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RRemoteService;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;

/**
 * @author maxuefeng
 * @since 2019/10/18
 */
public class RPCServiceTest {

    private RedissonClient client = null;

    @Before
    public void before() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.15.41.150:6379").setPassword("12323213");
        client = Redisson.create(config);
    }

    @Test
    public void serverProvider() throws IOException {
        // 获取远程注册服务代理对象
        RRemoteService rRemoteService = client.getRemoteService();

        // 注册远程服务
        RPCServiceImpl rpcServiceInstance = new RPCServiceImpl();
        rRemoteService.register(RPCService.class,rpcServiceInstance);

        // 阻塞线程一直提供服务
        System.in.read();
    }


    @Test
    public void serverConsumer(){
        // 获取远程注册服务代理对象
        RRemoteService rRemoteService = client.getRemoteService();

        // 获取目标服务对象
        RPCService rpcService = rRemoteService.get(RPCService.class);

        // 调用远程方法
        String result = rpcService.test();

        // 打印远程返回结果
        System.err.println(result);
    }


}
