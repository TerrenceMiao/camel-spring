package org.paradise.domain;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class Car {

    private static Optional<Insurance> insurance;

    public Car(Insurance insurance) {
        this.insurance = Optional.ofNullable(insurance);
    }

    public static <U> Optional<Insurance> getInsurance(U u) {
        return insurance;
    }

}
