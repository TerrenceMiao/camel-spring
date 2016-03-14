package org.paradise.monad;

/**
 * Created by terrence on 14/03/2016.
 */
public class TrySuccess<V> extends Try<V> {

    private V value;

    public TrySuccess(V value) {
        super();
        this.value = value;
    }

    @Override
    public Boolean isSuccess() {
        return true;
    }

    @Override
    public Boolean isFailure() {
        return false;
    }

    @Override
    public void throwException() {
    }

}
