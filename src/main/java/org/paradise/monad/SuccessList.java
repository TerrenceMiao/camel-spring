package org.paradise.monad;

import java.util.List;
import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class SuccessList<L, A> extends Success<List<L>, A> {

    public SuccessList(A value) {
        super(value);
    }

    public <B> Validation<List<L>, B> map(Function<? super A, ? extends B> mapper) {
        return new SuccessList(mapper.apply(value));
    }

    public <B> Validation<List<L>, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return (Validation<List<L>, B>) (result.isSuccess() ?
                new SuccessList(result.value) :
                new FailureList<>((List<L>) ((Failure<L, B>) result).left, result.value));
    }

}
