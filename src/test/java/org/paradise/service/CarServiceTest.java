package org.paradise.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;

import static org.junit.Assert.assertSame;

/**
 * Created by terrence on 12/03/2016.
 */
public class CarServiceTest {

    String insuranceName = "Pacific Insurance";

    Person person;

    CarService carService = new CarService();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCarInsuranceName() throws Exception {

        person = new Person(new Car(new Insurance(insuranceName)));
        assertSame("Incorrect car insurance name", insuranceName, carService.getCarInsuranceName(person));
    }

    @Test
    public void testGetCarInsuranceNameNull() throws Exception {

        person = new Person(null);
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));

        person = new Person(new Car(null));
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));

        person = new Person(new Car(new Insurance(null)));
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));
    }

}