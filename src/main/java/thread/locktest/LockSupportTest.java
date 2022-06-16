package thread.locktest;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    private static volatile int n = 0;
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            System.out.println("线程1开始执行");
            for (int i = 0; i < 10; i++) {
                n++;
                if (n == 10) {
                    System.out.println("线程一准备休眠");
                    LockSupport.park();
                    System.out.println("线程一被唤醒");
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            System.out.println("线程2开始执行");
            if(n == 10) {
                n = 0;
                System.out.println("线程二唤醒线程一");
                LockSupport.unpark(thread1);
            }
        }, "thread2");

        thread1.start();
        thread2.start();

        // 让主线程休眠
        Thread.sleep(1000);
        System.out.println(n);
    }
}
