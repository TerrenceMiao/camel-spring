package org.paradise.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;
import org.paradise.exception.NullValueException;

import static org.junit.Assert.assertSame;

/**
 * Created by terrence on 12/03/2016.
 */
public class PersonServiceTest {

    String insuranceName = "Pacific Insurance";
    String insurancePolicy = "Pacific Insurance Policy";

    Person person;

    PersonService personService = new PersonService();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCarInsuranceNameAndPolicy() throws Exception {

        person = new Person(new Car(new Insurance(insuranceName, insurancePolicy)));

        assertSame("Incorrect car insurance name", insuranceName, personService.getCarInsuranceName(person));
        assertSame("Incorrect car insurance policy", insurancePolicy, personService.getCarInsurancePolicy(person));
    }

    @Test
    public void testGetCarInsuranceNameUnknown() {

        person = new Person(null);
        assertSame("Incorrect car insurance name", "Unknown", personService.getCarInsuranceName(person));

        person = new Person(new Car(null));
        assertSame("Incorrect car insurance name", "Unknown", personService.getCarInsuranceName(person));

        person = new Person(new Car(new Insurance(null, insurancePolicy)));
        assertSame("Incorrect car insurance name", "Unknown", personService.getCarInsuranceName(person));
    }

    @Test(expected = NullValueException.class)
    public void testGetCarInsurancePolicyExceptionWhenPersonIsNull() throws Exception {

        person = new Person(null);
        personService.getCarInsurancePolicy(person);
    }

    @Test(expected = NullValueException.class)
    public void testGetCarInsurancePolicyExceptionWhenCarIsNull() throws Exception {

        person = new Person(new Car(null));
        personService.getCarInsurancePolicy(person);
    }

    @Test(expected = NullValueException.class)
    public void testGetCarInsurancePolicyExceptionWhenPolicyIsNull() throws Exception {

        person = new Person(new Car(new Insurance(insuranceName, null)));
        personService.getCarInsurancePolicy(person);
    }

}