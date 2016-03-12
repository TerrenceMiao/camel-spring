package org.paradise.domain;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class Car {

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }

}
