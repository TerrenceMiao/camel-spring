package org.paradise.monad;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class ValidationFailureList<L, A> extends ValidationFailure<List<L>, A> {

    public ValidationFailureList(List<L> left, A value) {
        super(left, value);
    }

    public <B> Validation<List<L>, B> map(Function<? super A, ? extends B> mapper) {
        return new ValidationFailureList(left, mapper.apply(value));
    }

    public <B> Validation<List<L>, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return (Validation<List<L>, B>) (result.isSuccess() ?
                new ValidationFailureList(left, result.value) :
                new ValidationFailureList<>(new ArrayList<L>(left) {{ add(((ValidationFailure<L, B>) result).left); }}, result.value));
    }

}
