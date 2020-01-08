package io.jopen.springboot.plugin.encryption.exception;

/**
 * <p>解密数据失败异常</p>
 */
public class DecryptBodyFailException extends RuntimeException {

    public DecryptBodyFailException() {
        super("Decrypting data failed. (解密数据失败)");
    }

    public DecryptBodyFailException(String message) {
        super(message);
    }
}