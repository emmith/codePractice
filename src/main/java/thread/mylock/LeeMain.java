package thread.mylock;

import lombok.SneakyThrows;

public class LeeMain {

    static int count = 0;
    static LeeLock leeLock = new LeeLock();

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            @SneakyThrows
            public void run() {

                for (int i = 0; i < 10000; i++) {
                    try {
                        leeLock.lock();
                        count++;
                    } finally {
                        leeLock.unlock();
                    }
                }


            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(count);
    }
}
