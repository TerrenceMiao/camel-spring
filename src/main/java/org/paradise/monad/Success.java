package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class Success<L, A> extends Validation<L, A> {

    public Success(A value) {
        super(value);
    }

    public <B> Validation<L, B> map(Function<? super A, ? extends B> mapper) {
        return success(mapper.apply(value));
    }

    public <B> Validation<L, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        return (Validation<L, B>) mapper.apply(value);
    }

    public boolean isSuccess() {
        return true;
    }

    public static <L, A> Success<L, A> success(A value) {
        return new Success<>(value);
    }

}
