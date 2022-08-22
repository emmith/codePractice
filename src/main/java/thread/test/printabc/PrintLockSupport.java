package thread.test.printabc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

public class PrintLockSupport {

    public static Map<Integer, Thread> map = new HashMap<>();

    public static volatile int flag = 1;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            map.putIfAbsent(0, Thread.currentThread());
            for (int i = 0; i < 20; i++) {
                while (flag % 2 == 0) LockSupport.park();
                System.out.printf("0 ");
                flag++;
                if (flag % 4 == 0) LockSupport.unpark(map.get(2));
                else if (flag % 4 == 2) LockSupport.unpark(map.get(1));
            }
        });

        // 打印奇数
        Thread thread2 = new Thread(() -> {
            map.putIfAbsent(1, Thread.currentThread());
            for (int i = 1; i <= 20; i += 2) {
                while (flag % 4 != 2) LockSupport.park();
                System.out.printf("%d ", i);
                flag++;
                LockSupport.unpark(map.get(0));
            }
        });

        // 打印偶数
        Thread thread3 = new Thread(() -> {
            map.putIfAbsent(2, Thread.currentThread());
            for (int i = 2; i <= 20; i += 2) {
                while (flag % 4 != 0) LockSupport.park();
                System.out.printf("%d ", i);
                flag++;
                LockSupport.unpark(map.get(0));
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
