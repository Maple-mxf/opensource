package dev.morphia.converters;


/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 */
public class ConverterNotFoundException extends RuntimeException {

    /**
     * Creates the ConverterNotFoundException.
     *
     * @param msg the io.jopen.springboot.encryption.exception message
     */
    public ConverterNotFoundException(final String msg) {
        super(msg);
    }
}
