package org.paradise.service;

import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class CarService {


    String getCarInsuranceName(Optional<Person> person) {

        return person
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

}
