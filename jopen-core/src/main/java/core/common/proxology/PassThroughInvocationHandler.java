package core.common.proxology;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author maxuefeng
 */
public class PassThroughInvocationHandler implements InvocationHandler {

	@SuppressWarnings("unchecked")
	public static <T> T proxying(T target, Class<T> iface) {
		return (T) Proxy.newProxyInstance(
				iface.getClassLoader(),
				new Class<?>[]{iface},
				new PassThroughInvocationHandler(target));
	}

	private final Object target;

	/**
	 * @param target
	 */
	public PassThroughInvocationHandler(Object target) {
		this.target = target;
	}

	/**
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}
}
