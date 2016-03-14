package org.paradise.domain;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class Person {

    private String name;
    private int age;

    private static Optional<Car> car;

    public Person(Car car) {
        this.car = Optional.ofNullable(car);
    }

    public static <U> Optional<Car> getCar(U u) {
        return car;
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

}
