package io.jopen.core.common.sign;

import io.jopen.core.common.code.rsaandbase64.RSAKeyPairGenerator;
import io.jopen.core.common.code.rsaandbase64.RSAUtil;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author maxuefeng
 */
public class ServerClientRSATest {

    // 服务端公钥对
    private String sPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDwjWoqCpb441HWzJXOzaOjr0DdsYb/aHKlkS9x29NIWtQVL7fOF43CsGruWQVCCy2e1S8d9aWHVp5IH2O7Udh3wyVV96U2Eq3vFxwXszDtpxaRG7JC6tf0TtDwXjA48jT7IGyt2FaLAsw+7/JTg+QP9Xt2lVyq5xbE4sV4FDgfwIDAQAB";
    private String sPriKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMPCNaioKlvjjUdbMlc7No6OvQN2xhv9ocqWRL3Hb00ha1BUvt84XjcKwau5ZBUILLZ7VLx31pYdWnkgfY7tR2HfDJVX3pTYSre8XHBezMO2nFpEbskLq1/RO0PBeMDjyNPsgbK3YVosCzD7v8lOD5A/1e3aVXKrnFsTixXgUOB/AgMBAAECgYAB4DaLazEcuE1+RY1x8ItRSmTdR6IWWpPWUSJPfz29y3gvyOAbW3rrpBq+/oPK/80N4pipRay0u4tGZd1OJU/tx9ekCD1ycfifI/ZVjCritxNaNVYAQWqzh88InnDhziDO9xo5dBZpPbqsQfLmPI4XT64UglORmDF81TLVB92WgQJBAPtyFezy04ps+01tWFIxLJ1QLn7ib1hJMX5g1r/GKBHyDgCxke3T0+9KL3QspHg04Wv+ykTPJdplx0ToJcdy73ECQQDHTel0GfJ1YVv90sI9WTE0LKqtARpYfyUlyFCvjp++1lPRPSZd/X4G+uWOcNOHdALlNaKaYnWttvlwMyZv2bbvAkAGeu03uxZale5wE2ZYT+4fa8aW/Iz0EIbXA/Xk4V19AOj9eZYSNAhdPWM1skTW2D4LA3kBBvmpxD+7deQrXLnxAkEAkWBndWSn0q8dptvhC1bP6TrblC5FxV+Y/SJMmIH0TAOSSAHOxqhtETJZ17i80wTwR7kDzpEbPYdGE218EAJCyQJBAOnqdQJlFi3x1IkSeWUJ/ok2Ekgln/eSjnv1NJOX+92nJTsCyNZtbmGYzs9/BVxlnj01u+FWRr5plc042vRLfJQ=";


    // 客户端的公私钥对
    private String cPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUgadJcExwYET+GozPGr5p0pGH7Jw0l+2kX2B7NZkOQQL4uNfWIvMwmuzNdewOL1//cS4zra4kL0Vbb5VcuvQYs8TxThVFEhRNS5iI3HuMnWo58ifCZDuG+CusNV8/PJj+JmvxuJat7O0DmZ08ZaER661AYAMkZb2AVS9kO1VrYwIDAQAB";
    private String cPriKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANSBp0lwTHBgRP4ajM8avmnSkYfsnDSX7aRfYHs1mQ5BAvi419Yi8zCa7M117A4vX/9xLjOtriQvRVtvlVy69BizxPFOFUUSFE1LmIjce4ydajnyJ8JkO4b4K6w1Xz88mP4ma/G4lq3s7QOZnTxloRHrrUBgAyRlvYBVL2Q7VWtjAgMBAAECgYEAi9Z9tM/XHSPcgrIJ8scQgiZ0adODvwAy+zVgEfPlMgIJ6I4KSflZuqRYK6ProDIAo+Fgm9UwgNMyesbkrM7BjMTV5bjLuCx6DjnXjMnMOePU5WK1G45lUVcxllO+ORadmMSV4xQ6rPQCyAngBQFTbwize4e/Ocpx21T+Hxhj2VECQQDqjaT6W8NpnqiPQIvWUPw9jBI2nEbl2WOjn6rG1Af/gpdRumkC7Q8QrfaUph/byN/uSVJXuo+/GfrfbiiB8nF1AkEA5+/xZ9z5jdpEm3J7vWVGFwDl2y5qLNP+ba/KjPgOGLUBgzLrS6tMpC/5DvAEwB8eGmQbRRy0LG20verPJmQ2dwJBAJgSrCNyeXkyWAFBDSkwKsrQ1UI5ArKlxrdgnz34XRMpnWhs3wb5oinjUJtCNieuzplVSqD8oZR6iLByYKsYAL0CQQDaN2pNah5xVBaQmlnJi+sx4WphmX3Tx3DuCiLsFsZMgKB3rAStIPHu2CPf5frSN3S/J8hwcd0in+UZej6DxG1pAkBdi5cwqYYKVwZkx+xE3no41EN7Owj/Hns5tH1nwxC6JvPjXgV8lgwZeLXmGXm4NeWQUnCT06BHOeyHixTOula2";

    /**
     * sPubKey:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDwjWoqCpb441HWzJXOzaOjr0DdsYb/aHKlkS9x29NIWtQVL7fOF43CsGruWQVCCy2e1S8d9aWHVp5IH2O7Udh3wyVV96U2Eq3vFxwXszDtpxaRG7JC6tf0TtDwXjA48jT7IGyt2FaLAsw+7/JTg+QP9Xt2lVyq5xbE4sV4FDgfwIDAQAB
     * sPriKey:MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMPCNaioKlvjjUdbMlc7No6OvQN2xhv9ocqWRL3Hb00ha1BUvt84XjcKwau5ZBUILLZ7VLx31pYdWnkgfY7tR2HfDJVX3pTYSre8XHBezMO2nFpEbskLq1/RO0PBeMDjyNPsgbK3YVosCzD7v8lOD5A/1e3aVXKrnFsTixXgUOB/AgMBAAECgYAB4DaLazEcuE1+RY1x8ItRSmTdR6IWWpPWUSJPfz29y3gvyOAbW3rrpBq+/oPK/80N4pipRay0u4tGZd1OJU/tx9ekCD1ycfifI/ZVjCritxNaNVYAQWqzh88InnDhziDO9xo5dBZpPbqsQfLmPI4XT64UglORmDF81TLVB92WgQJBAPtyFezy04ps+01tWFIxLJ1QLn7ib1hJMX5g1r/GKBHyDgCxke3T0+9KL3QspHg04Wv+ykTPJdplx0ToJcdy73ECQQDHTel0GfJ1YVv90sI9WTE0LKqtARpYfyUlyFCvjp++1lPRPSZd/X4G+uWOcNOHdALlNaKaYnWttvlwMyZv2bbvAkAGeu03uxZale5wE2ZYT+4fa8aW/Iz0EIbXA/Xk4V19AOj9eZYSNAhdPWM1skTW2D4LA3kBBvmpxD+7deQrXLnxAkEAkWBndWSn0q8dptvhC1bP6TrblC5FxV+Y/SJMmIH0TAOSSAHOxqhtETJZ17i80wTwR7kDzpEbPYdGE218EAJCyQJBAOnqdQJlFi3x1IkSeWUJ/ok2Ekgln/eSjnv1NJOX+92nJTsCyNZtbmGYzs9/BVxlnj01u+FWRr5plc042vRLfJQ=
     *
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void generateServerKeys() throws NoSuchAlgorithmException {

        RSAKeyPairGenerator serverGenerator = new RSAKeyPairGenerator();
        System.out.println(Base64.getEncoder().encodeToString(serverGenerator.getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(serverGenerator.getPrivateKey().getEncoded()));
    }

    /**
     * cPubKey:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUgadJcExwYET+GozPGr5p0pGH7Jw0l+2kX2B7NZkOQQL4uNfWIvMwmuzNdewOL1//cS4zra4kL0Vbb5VcuvQYs8TxThVFEhRNS5iI3HuMnWo58ifCZDuG+CusNV8/PJj+JmvxuJat7O0DmZ08ZaER661AYAMkZb2AVS9kO1VrYwIDAQAB
     * cPriKey:MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANSBp0lwTHBgRP4ajM8avmnSkYfsnDSX7aRfYHs1mQ5BAvi419Yi8zCa7M117A4vX/9xLjOtriQvRVtvlVy69BizxPFOFUUSFE1LmIjce4ydajnyJ8JkO4b4K6w1Xz88mP4ma/G4lq3s7QOZnTxloRHrrUBgAyRlvYBVL2Q7VWtjAgMBAAECgYEAi9Z9tM/XHSPcgrIJ8scQgiZ0adODvwAy+zVgEfPlMgIJ6I4KSflZuqRYK6ProDIAo+Fgm9UwgNMyesbkrM7BjMTV5bjLuCx6DjnXjMnMOePU5WK1G45lUVcxllO+ORadmMSV4xQ6rPQCyAngBQFTbwize4e/Ocpx21T+Hxhj2VECQQDqjaT6W8NpnqiPQIvWUPw9jBI2nEbl2WOjn6rG1Af/gpdRumkC7Q8QrfaUph/byN/uSVJXuo+/GfrfbiiB8nF1AkEA5+/xZ9z5jdpEm3J7vWVGFwDl2y5qLNP+ba/KjPgOGLUBgzLrS6tMpC/5DvAEwB8eGmQbRRy0LG20verPJmQ2dwJBAJgSrCNyeXkyWAFBDSkwKsrQ1UI5ArKlxrdgnz34XRMpnWhs3wb5oinjUJtCNieuzplVSqD8oZR6iLByYKsYAL0CQQDaN2pNah5xVBaQmlnJi+sx4WphmX3Tx3DuCiLsFsZMgKB3rAStIPHu2CPf5frSN3S/J8hwcd0in+UZej6DxG1pAkBdi5cwqYYKVwZkx+xE3no41EN7Owj/Hns5tH1nwxC6JvPjXgV8lgwZeLXmGXm4NeWQUnCT06BHOeyHixTOula2
     *
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void generateClientKeys() throws NoSuchAlgorithmException {

        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        System.out.println(Base64.getEncoder().encodeToString(generator.getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(generator.getPrivateKey().getEncoded()));
    }

    public String getServerPubKey() {
        return this.sPubKey;
    }


    // 第一次交互
    public String client1() throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException {
        // 1 获取服务的pubKey
        String serverPubKey = getServerPubKey();

        // 2  serverPublicKey作为公钥，clientPublicKey作为明文对clientPublicKey进行RSA加密
        byte[] encryptClientPubKey = RSAUtil.encrypt(cPubKey, serverPubKey);

        // 3
        return new String(encryptClientPubKey);
    }

    @Test
    public void testClient1() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {
        System.err.println(client1());
    }

    // 服务端接受加密之后的ClientPubKey
    public void server1() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException {

        // 1 获取客户端加密之后的key
        String encryptClientPubKey = client1();

        // 2 解密获得到客户端的clientPubKey
        String decryptClientPubKey = RSAUtil.decrypt(encryptClientPubKey, sPriKey);

        // 3 给客户端返回Key
        System.err.println();

    }

}
