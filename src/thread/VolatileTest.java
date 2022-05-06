package thread;

public class VolatileTest {
    public static volatile int j = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                j++;
            }
        });
        thread.wait();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                j++;
            }
        });
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(j);
    }
}
