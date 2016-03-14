package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 14/03/2016.
 */
public class TrySuccess<A> extends Try<A> {

    public TrySuccess(A value) {
        super(value);
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }

    @Override
    public Boolean isFailure() {
        return false;
    }

    @Override
    public void throwException() {
    }

    @Override
    public <B> Try<B> map(Function<? super A, ? extends B> mapper) {
        return success(mapper.apply(value));
    }

    @Override
    public <B> Try<B> flatMap(Function<? super A, Try<? extends B>> mapper) {
        return (Try<B>) mapper.apply(value);
    }

}
