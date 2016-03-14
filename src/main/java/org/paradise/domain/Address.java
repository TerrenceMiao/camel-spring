package org.paradise.domain;

import org.paradise.exception.NullValueException;
import org.paradise.monad.Try;
import org.paradise.monad.TryFailure;
import org.paradise.monad.TrySuccess;

/**
 * Created by terrence on 14/03/2016.
 */
public class Address {

    private static City city;

    public Address(City city) {

        this.city = city;
    }

    public static <U> Try<City> getCity(U u) {

        return city == null
                ? new TryFailure<>(city).failure(u.toString(), new NullValueException("City is NULL"))
                : new TrySuccess(city);
    }

}
