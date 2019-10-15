package core.common.code;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

/**
 * SHA加密工具类
 *
 * @author maxuefeng
 */
public class SHA1Helper {

    /**
     * @return
     */
    public static String nonce() {
        Random random = new Random();
        return MD5Helper.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    /**
     * @return
     */
    public static String timestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 创建签名SHA1
     *
     * @param signParams
     * @return
     * @throws Exception
     */
    public static String generateSHA1Sign(SortedMap<String, String> signParams) throws Exception {

        StringBuilder sb = new StringBuilder();

        Set es = signParams.entrySet();

        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k).append("=").append(v).append("&");
            //要采用URLENCODER的原始值！
        }
        String params = sb.substring(0, sb.lastIndexOf("&"));
        System.out.println("sha1 sb:" + params);
        return getSha1(params);

    }

    /**
     * Sha1签名
     *
     * @param origin
     * @return
     */
    public static String getSha1(String origin) {

        if (StringUtils.isBlank(origin)) return null;

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(origin.getBytes("GBK"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;

            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(buf);
        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}
