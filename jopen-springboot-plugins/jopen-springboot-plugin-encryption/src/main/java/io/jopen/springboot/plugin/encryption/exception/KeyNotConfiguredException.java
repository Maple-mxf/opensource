package io.jopen.springboot.plugin.encryption.exception;


import lombok.NoArgsConstructor;

/**
 * <p>未配置KEY运行时异常</p>
 */
@NoArgsConstructor
public class KeyNotConfiguredException extends RuntimeException {

    public KeyNotConfiguredException(String message) {
        super(message);
    }
}
