package io.jopen.core.common.proxology.beans;

import io.jopen.core.common.proxology.EqualisableByState;
import io.jopen.core.common.proxology.proxies.Proxies;

/**
 * @author maxuefeng
 */
public final class BeanProxy {


    private BeanProxy() {
    }

    /**
     * @param proxyClass
     * @param <T>
     * @return
     */
    public static <T> T proxying(Class<T> proxyClass) {

        //
        BeanProxySchema schema = BeanProxySchema.forClass(proxyClass);

        //
        BeanProxyStorage storage = schema.createStorage();

        //
        return Proxies.simpleProxy(proxyClass, storage.getMethodInterpreter(), EqualisableByState.class);
    }

}
