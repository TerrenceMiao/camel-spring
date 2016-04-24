package org.paradise.service;

import org.paradise.domain.Address;
import org.paradise.domain.Car;
import org.paradise.domain.City;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;
import org.paradise.exception.NullValueException;
import org.paradise.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
@Service
public class PersonService {

    private final PersonValidator personValidator;

    public PersonService(PersonValidator personValidator) {
        this.personValidator = personValidator;
    }

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

    Boolean isPersonValid(Person person) {

        return personValidator.validate(person);
    }

    private <U> U process(U u) {
        return u;
    }

}
