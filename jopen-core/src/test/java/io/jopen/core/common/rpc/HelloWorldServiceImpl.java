package io.jopen.core.common.rpc;

/**
 * @author maxuefeng
 * @since 2019/9/11
 */
public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public String sayHello(String msg) {
        String result = "hello world " + msg;
        System.out.println(result);
        return result;
    }
}
