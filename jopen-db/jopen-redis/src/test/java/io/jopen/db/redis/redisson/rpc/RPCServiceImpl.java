package io.jopen.db.redis.redisson.rpc;

/**
 * @author maxuefeng
 * @since 2019/10/18
 */
public class RPCServiceImpl implements RPCService {

    @Override
    public String test() {
        return "我是被调用方法";
    }
}
