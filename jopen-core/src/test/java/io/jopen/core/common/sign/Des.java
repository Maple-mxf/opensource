package io.jopen.core.common.sign;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author maxuefeng
 * @since 2019/11/6
 */
public class Des {

    static {
        // add BC provider
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     *
     * @param encryptText 需要加密的信息
     * @param key 加密密钥
     * @return 加密后Base64编码的字符串
     */
    public static String encrypt(String encryptText, String key) {

        if (encryptText == null || key == null) {
            throw new IllegalArgumentException("encryptText or key must not be null");
        }

        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(encryptText.getBytes(Charset.forName("UTF-8")));
            return Base64.getEncoder().encodeToString(bytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | NoSuchPaddingException
                | BadPaddingException | NoSuchProviderException | IllegalBlockSizeException e) {
            throw new RuntimeException("encrypt failed", e);
        }
    }

    /**
     * 解密
     *
     * @param decryptText 需要解密的信息
     * @param key 解密密钥，经过Base64编码
     * @return 解密后的字符串
     */
    public static  String decrypt(String decryptText, String key) {

        if (decryptText == null || key == null) {
            throw new IllegalArgumentException("decryptText or key must not be null");
        }

        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(decryptText));
            return new String(bytes, Charset.forName("UTF-8"));

        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | NoSuchPaddingException
                | BadPaddingException | NoSuchProviderException | IllegalBlockSizeException e) {
            throw new RuntimeException("decrypt failed", e);
        }
    }

    public static void main(String[] args) {
        String decrypt = decrypt("KscTwc1CgJibpyfNLFZiIl6GsbkugiEZ", "BvQf0Jz3Puv7TTxi");
        System.err.println(decrypt);
    }
}
