package com.badun.akkakamondemo.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by Artsiom Badun.
 */
public class Sleeper {

    public static void sleep(int sec) {
        sleep(sec, TimeUnit.SECONDS);
    }

    public static void sleep(int duration, TimeUnit timeUnit) {
        try {
            Thread.sleep(timeUnit.toMillis(duration));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
