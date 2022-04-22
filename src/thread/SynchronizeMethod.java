package thread;

public class SynchronizeMethod {
    private volatile int count = 0;

    //这个方法和add方法等价，都是以当前实例为锁，锁住了非原子操作
    public synchronized void addSyn(int n) {
        for (int i = 0; i < 10000; i++)
            count += n;
    }

    public synchronized void decSyn(int n) {
        for (int i = 0; i < 10000; i++)
            count -= n;
    }

    public int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizeMethod counter1 = new SynchronizeMethod();

        Thread thread1 = new Thread(() -> {
            counter1.addSyn(100);
        });
        Thread thread2 = new Thread(() -> {
            counter1.decSyn(100);
        });
        Thread thread3 = new Thread(() -> {
            counter1.addSyn(10);
        });
        Thread thread4 = new Thread(() -> {
            counter1.decSyn(10);
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        System.out.println("counter1 " + counter1.count);
    }
}
