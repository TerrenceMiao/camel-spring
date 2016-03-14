package org.paradise.monad;

import java.util.List;
import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class ValidationSuccessList<L, A> extends ValidationSuccess<List<L>, A> {

    public ValidationSuccessList(A value) {
        super(value);
    }

    public <B> Validation<List<L>, B> map(Function<? super A, ? extends B> mapper) {
        return new ValidationSuccessList(mapper.apply(value));
    }

    public <B> Validation<List<L>, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return (Validation<List<L>, B>) (result.isSuccess() ?
                new ValidationSuccessList(result.value) :
                new ValidationFailureList<>((List<L>) ((ValidationFailure<L, B>) result).left, result.value));
    }

}
