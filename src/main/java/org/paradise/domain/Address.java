package org.paradise.domain;

import org.paradise.exception.NullValueException;
import org.paradise.monad.Try;

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
                ? Try.failure(u.toString(), new NullValueException("City is NULL"))
                : Try.success(city);
    }

}
