package utils;

import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadUtil {
    public static final Random rm = new Random();

    public static void randomSleep() {
        int time = ThreadLocalRandom.current().nextInt(1, 10000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void millsSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printThreadInfo(String msg) {
        String res = new StringJoiner("|\t\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(msg)
                .toString();
        System.out.println(res);
    }
}
