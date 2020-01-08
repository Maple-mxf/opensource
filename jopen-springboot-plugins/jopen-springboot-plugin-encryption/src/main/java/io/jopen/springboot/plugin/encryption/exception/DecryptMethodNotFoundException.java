package io.jopen.springboot.plugin.encryption.exception;

/**
 * <p>加密方式未找到或未定义异常</p>
 */
public class DecryptMethodNotFoundException extends RuntimeException {

    public DecryptMethodNotFoundException() {
        super("Decryption method is not defined. (解密方式未定义)");
    }

    public DecryptMethodNotFoundException(String message) {
        super(message);
    }
}
