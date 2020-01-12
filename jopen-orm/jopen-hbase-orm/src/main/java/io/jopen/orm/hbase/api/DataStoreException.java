
package io.jopen.orm.hbase.api;

public class DataStoreException extends RuntimeException {

    private static final long serialVersionUID = -1363433871972493184L;

    public DataStoreException() {
        super();
    }

    public DataStoreException(String message) {
        super(message);
    }

    public DataStoreException(Throwable cause) {
        super(cause);
    }

    public DataStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
