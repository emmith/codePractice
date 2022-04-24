package utils;

import java.util.Random;

public class ThreadUtil {
    public static final Random rm = new Random();

    public static void randomSleep() {
        int time = rm.nextInt(1, 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
