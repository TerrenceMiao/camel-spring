package org.paradise.domain;

import org.paradise.exception.NullValueException;
import org.paradise.monad.Try;
import org.paradise.monad.TryFailure;
import org.paradise.monad.TrySuccess;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class Person {

    private String name;
    private int age;

    private static Optional<Car> car;

    private static Address address;


    public Person(Car car) {
        this.car = Optional.ofNullable(car);
    }

    public static <U> Optional<Car> getCar(U u) {
        return car;
    }

    public static <U> Try<Address> getAddress(U u) {

        return address == null
                ? new TryFailure<>(address).failure(u.toString(), new NullValueException("Address is NULL"))
                : new TrySuccess(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person {" +
                "name = [" + name + ']' +
                ", age = [" + age + ']' +
                ", car = [" + car + ']' +
                ", address = [" + address + ']' +
                '}';
    }

}
