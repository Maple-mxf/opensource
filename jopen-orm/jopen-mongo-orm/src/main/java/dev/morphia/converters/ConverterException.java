package dev.morphia.converters;


/**
 * Indicates an error occurred trying to convert a value.
 */
public class ConverterException extends RuntimeException {
    /**
     * Creates the ConverterException.
     *
     * @param msg the io.jopen.springboot.encryption.exception message
     */
    public ConverterException(final String msg) {
        super(msg);
    }
}
