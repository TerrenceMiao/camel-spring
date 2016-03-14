package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 14/03/2016.
 */
public class TryFailure<A> extends Try<A> {

    private RuntimeException exception;

    public TryFailure(A value) {
        super(value);
    }

    public TryFailure(A value, String message) {
        super(value);
        this.exception = new IllegalStateException(message);
    }

    public TryFailure(A value, String message, Exception e) {
        super(value);
        this.exception = new IllegalStateException(message, e);
    }

    public TryFailure(A value, Exception e) {
        super(value);
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

    @Override
    public <B> Try<B> map(Function<? super A, ? extends B> mapper) {
        return new TryFailure<>(mapper.apply(value));
    }

    @Override
    public <B> Try<B> flatMap(Function<? super A, Try<? extends B>> mapper) {
        Try<? extends B> result = mapper.apply(value);

        return result.isSuccess() ? new TrySuccess<>(result.value) : new TryFailure<>(((Try<B>) result).value);
    }

    public RuntimeException getException() {
        return exception;
    }

}

