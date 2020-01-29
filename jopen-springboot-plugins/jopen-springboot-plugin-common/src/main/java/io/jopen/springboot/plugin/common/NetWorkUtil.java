package io.jopen.springboot.plugin.common;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * IP地址
 */
public class NetWorkUtil {

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (Strings.isNullOrEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String getLocalIP() throws SocketException {

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {

            NetworkInterface netInterface = networkInterfaces.nextElement();

            List<InterfaceAddress> tmp = netInterface.getInterfaceAddresses();

            for (InterfaceAddress v : tmp) {
                String address = v.getAddress().getHostAddress().replace("/", "");

                if (address.startsWith("127.0") || address.startsWith("0:") || address.startsWith("0.")) continue;

                // address[0] is not a Number  continue
                if (!Character.isDigit(address.charAt(0))) continue;

                //注意 点运算符是Java中的特殊字符需要转义
                String[] split = address.split("\\.");

                //
                if (split.length != 0) {
                    int n = 0;
                    for (String var : split) {
                        Pattern pattern = Pattern.compile("[0-9]*");
                        if (pattern.matcher(var).matches()) {
                            n++;
                        }
                    }
                    //
                    if (n == 4) {
                        return address;
                    }
                }
            }
        }
        return null;
    }

}
