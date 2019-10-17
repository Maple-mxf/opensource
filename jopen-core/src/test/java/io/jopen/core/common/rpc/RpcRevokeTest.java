package io.jopen.core.common.rpc;

import org.junit.Test;

/**
 * @author maxuefeng
 * @since 2019/9/11
 */
public class RpcRevokeTest {

    @Test
    public void simpleTest() {
//        HelloWorldService helloWorldService = (HelloWorldService) RPCProxyClient.getProxy(HelloWorldService.class);
//        helloWorldService.sayHello("test");

        Object proxy = RPCProxyClient.getProxy(HelloWorldService.class);

        System.err.println("HelloWorld");
    }
}
