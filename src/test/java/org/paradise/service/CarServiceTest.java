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
    String insurancePolicy = "Pacific Insurance Policy";

    Person person;

    CarService carService = new CarService();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCarInsuranceNameAndPolicy() throws Exception {

        person = new Person(new Car(new Insurance(insuranceName, insurancePolicy)));

        assertSame("Incorrect car insurance name", insuranceName, carService.getCarInsuranceName(person));
        assertSame("Incorrect car insurance policy", insurancePolicy, carService.getCarInsurancePolicy(person));
    }

    @Test
    public void testGetCarInsuranceNameUnknown() {

        person = new Person(null);
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));

        person = new Person(new Car(null));
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));

        person = new Person(new Car(new Insurance(null, insurancePolicy)));
        assertSame("Incorrect car insurance name", "Unknown", carService.getCarInsuranceName(person));
    }

    @Test(expected = RuntimeException.class)
    public void testGetCarInsurancePolicyExceptionWhenPersonIsNull() {

        person = new Person(null);
        carService.getCarInsurancePolicy(person);
    }

    @Test(expected = RuntimeException.class)
    public void testGetCarInsurancePolicyExceptionWhenCarIsNull() {

        person = new Person(new Car(null));
        carService.getCarInsurancePolicy(person);
    }

    @Test(expected = RuntimeException.class)
    public void testGetCarInsurancePolicyExceptionWhenPolicyIsNull() {

        person = new Person(new Car(new Insurance(insuranceName, null)));
        carService.getCarInsurancePolicy(person);
    }

}