package org.paradise.exception;

/**
 * Created by terrence on 14/03/2016.
 */
public class NullValueException extends Exception {

    public NullValueException() {
    }

    public NullValueException(String message) {
        super(message);
    }

    public NullValueException(Throwable cause) {
        super(cause);
    }

    public NullValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
