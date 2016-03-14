package org.paradise.validator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;
import org.paradise.monad.ValidationFailure;
import org.paradise.monad.ValidationSuccess;

import static org.junit.Assert.assertTrue;

/**
 * Created by terrence on 12/03/2016.
 */
public class PersonValidatorTest {

    String insuranceName = "Pacific Insurance";
    String insurancePolicy = "Pacific Insurance Policy";

    Person person = new Person(new Car(new Insurance(insuranceName, insurancePolicy)));

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidateAgePassed() throws Exception {

        person.setAge(1);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationSuccess);

        person.setAge(129);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationSuccess);

        person.setAge(100);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationSuccess);
    }

    @Test
    public void testValidateAgeFailed() throws Exception {

        person.setAge(0);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationFailure);

        person.setAge(130);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationFailure);

        person.setAge(-1);
        assertTrue("Incorrect age", PersonValidator.validateAge(person) instanceof ValidationFailure);
    }

    @Test
    public void testValidateAge() throws Exception {

        person.setAge(30);
        person.setName("Terrence");

        new ValidationSuccess<>(person)
                .flatMap(PersonValidator::validateAge)
                .flatMap(PersonValidator::validateName);
    }

    @Test
    public void testValidateName() throws Exception {

    }

}