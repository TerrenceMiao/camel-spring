package org.paradise.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.paradise.domain.Car;
import org.paradise.domain.Insurance;
import org.paradise.domain.Person;
import org.paradise.exception.NullValueException;
import org.paradise.validator.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by terrence on 12/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    String insuranceName = "Pacific Insurance";
    String insurancePolicy = "Pacific Insurance Policy";

    Person person;

    @InjectMocks
    PersonService personService;

    @Mock
    PersonValidator mockPersonValidator;

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

    @Test
    public void testIsPersonValid() throws Exception {

        person = new Person(new Car(new Insurance(insuranceName, null)));
        when(mockPersonValidator.validate(person)).thenReturn(Boolean.TRUE);

        assertTrue(personService.isPersonValid(person));
    }

}