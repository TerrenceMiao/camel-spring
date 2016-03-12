package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public abstract class Validation<L, A> {

    protected final A value;

    public Validation(A value) {
        this.value = value;
    }

    public abstract <B> Validation<L, B> map(Function<? super A, ? extends B> mapper);

    public abstract <B> Validation<L, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper);

    public abstract boolean isSuccess();

}
