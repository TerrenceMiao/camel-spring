package org.paradise.monad;

/**
 * Created by terrence on 14/03/2016.
 */
public class TryFailure<V> extends Try<V> {

    private RuntimeException exception;

    public TryFailure(String message) {
        super();
        this.exception = new IllegalStateException(message);
    }

    public TryFailure(String message, Exception e) {
        super();
        this.exception = new IllegalStateException(message, e);
    }

    public TryFailure(Exception e) {
        super();
        this.exception = new IllegalStateException(e);
    }

    @Override
    public Boolean isSuccess() {
        return false;
    }

    @Override
    public Boolean isFailure() {
        return true;
    }

    @Override
    public void throwException() {
        throw this.exception;
    }

    public RuntimeException getException() {
        return exception;
    }

}

