package org.paradise.monad;

import java.util.function.Consumer;

/**
 * Created by terrence on 14/03/2016.
 */
public abstract class Try<V> {

    public abstract Boolean isSuccess();

    public abstract Boolean isFailure();

    public abstract void throwException();

    public static <V> Try<V> failure(String message) {
        return new Failure<>(message);
    }

    public static <V> Try<V> failure(Exception e) {
        return new Failure<>(e);
    }

    public static <V> Try<V> failure(String message, Exception e) {
        return new Failure<>(message, e);
    }

    public static <V> Try<V> success(V value) {
        return new Success<>(value);
    }

    public void ifPresent(Consumer c) {
        if (isSuccess()) {
            c.accept(successValue());
        }
    }

    public void ifPresentOrThrow(Consumer<V> c) {
        if (isSuccess()) {
            c.accept(successValue());
        } else {
            throw ((Failure<V>) this).getException();
        }
    }
    public Try<RuntimeException> ifPresentOrFail(Consumer<V> c) {
        if (isSuccess()) {
            c.accept(successValue());
            return failure("Failed to fail!");
        } else {
            return success(failureValue());
        }
    }

    private <V> V successValue() {
        return null;
    }

    private <V> V failureValue() {
        return null;
    }

}
