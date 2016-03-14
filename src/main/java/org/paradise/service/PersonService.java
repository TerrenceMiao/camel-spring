package org.paradise.service;

import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;
import org.paradise.exception.NullValueException;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class PersonService {

    String getCarInsuranceName(Person person) {

        return Optional.of(person)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown")
                ;
    }

    String getCarInsurancePolicy(Person person) throws NullValueException {

        return Optional.of(person)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getPolicy)
                .orElseThrow(() -> new NullValueException())
                ;
    }

    String getAddressCityName(Person person) {

        return person.getAddress(person)
                .ifPresent(person);
                ;
    }

}
