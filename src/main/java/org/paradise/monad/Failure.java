package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class Failure<L, A> extends Validation<L, A> {

    protected final L left;

    public Failure(L left, A value) {
        super(value);
        this.left = left;
    }

    public <B> Validation<L, B> map(Function<? super A, ? extends B> mapper) {
        return new Failure(left, mapper.apply(value));
    }

    public <B> Validation<L, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return result.isSuccess() ? new Failure(left, result.value) : new Failure(((Failure<L, B>) result).left, result.value);
    }

    public boolean isSuccess() {
        return false;
    }

}
