package org.paradise.service;

import org.paradise.domain.*;
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

    void getAddressCityNameIfPresent(Person person) {

        person.getAddress(person)
                .flatMap(Address::getCity)
                .ifPresent(this::process)
                .map(City::getName)
                ;
    }

    void getAddressCityNameIfPresentOrFail(Person person) {

        person.getAddress(person)
                .flatMap(Address::getCity)
                .ifPresentOrFail(this::process)
                .map(City::getName)
        ;
    }

    void getAddressCityNameIfPresentOrThrow(Person person) {

        person.getAddress(person)
                .flatMap(Address::getCity)
                .ifPresentOrThrow(this::process)
                .map(City::getName)
        ;
    }

    private <U> U process(U u) {
        return u;
    }

}
