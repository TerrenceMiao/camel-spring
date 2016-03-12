package org.paradise.monad;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by terrence on 12/03/2016.
 */
public class FailureList<L, A> extends Failure<List<L>, A> {

    public FailureList(List<L> left, A value) {
        super(left, value);
    }

    public <B> Validation<List<L>, B> map(Function<? super A, ? extends B> mapper) {
        return new FailureList(left, mapper.apply(value));
    }

    public <B> Validation<List<L>, B> flatMap(Function<? super A, Validation<?, ? extends B>> mapper) {
        Validation<?, ? extends B> result = mapper.apply(value);

        return (Validation<List<L>, B>) (result.isSuccess() ?
                new FailureList(left, result.value) :
                new FailureList<>(new ArrayList<L>(left) {{ add(((Failure<L, B>) result).left); }}, result.value));
    }

}
