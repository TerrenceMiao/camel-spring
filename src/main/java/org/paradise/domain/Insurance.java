package org.paradise.domain;

/**
 * Created by terrence on 12/03/2016.
 */
public class Insurance {

    private static String name;
    private static String policy;

    public Insurance(String name, String policy) {
        this.name = name;
        this.policy = policy;
    }

    public static <U> String getName(U u) {
        return name;
    }

    public static <U> String getPolicy(U u) {
        return policy;
    }

}
