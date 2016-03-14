package org.paradise.monad;

import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class ValidationFailure<L, A> extends Validation<L, A> {

    protected final L left;

    public ValidationFailure(L left, A value) {
        super(value);
        this.left = left;
    }

    public <B> Validation<L, B> map(Function<? super A, ? extends B> mapper) {
        return new ValidationFailure(left, mapper.apply(value));
    }

    public <B> Validation<L, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return result.isSuccess() ? new ValidationFailure(left, result.value) : new ValidationFailure(((ValidationFailure<L, B>) result).left, result.value);
    }

    public boolean isSuccess() {
        return false;
    }

}
