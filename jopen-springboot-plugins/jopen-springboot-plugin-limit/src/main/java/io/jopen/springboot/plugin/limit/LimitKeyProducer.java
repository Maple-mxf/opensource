package io.jopen.springboot.plugin.limit;

import io.jopen.springboot.plugin.common.NetWorkUtil;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author maxuefeng
 * @since 2020/1/27
 */
public interface LimitKeyProducer {

    /**
     * 根据请求获取限流Key
     * 如果限流方案为根据IP限流 {@link HttpServletRequest#getRemoteAddr()}
     * 如果根据token限流，则需要用户自定义实现此方法 {@link LimitKeyProducer#key(HttpServletRequest)}
     *
     * @param request
     * @return
     */
    String key(HttpServletRequest request);

    /**
     * @see HttpServletRequest#getRemoteAddr()
     */
    final class IPLimitKeyStrategy implements LimitKeyProducer {
        @Override
        @NonNull
        public String key(@NonNull HttpServletRequest request) {
            return NetWorkUtil.getIpAddr(request);
        }
    }
}
