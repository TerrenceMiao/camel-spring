package org.paradise.monad;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by terrence on 14/03/2016.
 */
public abstract class Try<A> {

    protected A value;

    public Try(A value) {
        this.value = value;
    }

    public abstract Boolean isSuccess();

    public abstract Boolean isFailure();

    public abstract void throwException();

    public abstract <B> Try<B> map(Function<? super A, ? extends B> mapper);

    public abstract <B> Try<B> flatMap(Function<? super A, Try<? extends B>> mapper);


    public <A> Try<A> failure(String message) {
        return new TryFailure<>(message);
    }

    public <A> Try<A> failure(Exception e) {
        return new TryFailure<>(e);
    }

    public <A> Try<A> failure(String message, Exception e) {
        return new TryFailure<>(message, e);
    }

    public <A> Try<A> success(A value) {
        return new TrySuccess<>(value);
    }

    public void ifPresent(Consumer c) {
        if (isSuccess()) {
            c.accept(successValue());
        }
    }

    public void ifPresentOrThrow(Consumer<A> c) {
        if (isSuccess()) {
            c.accept(successValue());
        } else {
            throw ((TryFailure<A>) this).getException();
        }
    }
    public Try<RuntimeException> ifPresentOrFail(Consumer<A> c) {
        if (isSuccess()) {
            c.accept(successValue());
            return failure("Failed to fail!");
        } else {
            return success(failureValue());
        }
    }

    private <A> A successValue() {
        return null;
    }

    private <A> A failureValue() {
        return null;
    }

}
