package com.airtel.gurinder.utility;


import java.util.UUID;

/**
 * Created by gurinder on 9/7/16.
 */
public class RandomTokenUtililty {

    public static String getRandomToken() {
        UUID uuid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        return uuid.randomUUID().toString();
    }
}
