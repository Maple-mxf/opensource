package io.jopen.springboot.plugin.encryption.exception;

/**
 * <p>加密方式未找到或未定义异常</p>
 */
public class EncryptMethodNotFoundException extends RuntimeException {

    public EncryptMethodNotFoundException() {
        super("Encryption method is not defined. (加密方式未定义)");
    }

    public EncryptMethodNotFoundException(String message) {
        super(message);
    }
}
