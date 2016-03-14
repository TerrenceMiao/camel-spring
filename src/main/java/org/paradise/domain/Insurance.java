package org.paradise.domain;

import java.util.Optional;

/**
 * Created by terrence on 12/03/2016.
 */
public class Insurance {

    private static String name;

    public Insurance(String name) {
        this.name = name;
    }

    public static <U> String getName(U u) {
        return name;
    }

}
