package org.paradise.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by terrence on 12/03/2016.
 */
public class CarServiceTest {

    Optional<Insurance> insurance = Optional.of(new Insurance());
    Optional<Car> car = Optional.of(new Car());
    Optional<Person> person = Optional.of(new Person());

    CarService carService = new CarService();

    @Before
    public void setUp() throws Exception {

        car.get().setInsurance(insurance);
        person.get().setCar(car);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCarInsuranceName() throws Exception {

        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));
    }

}