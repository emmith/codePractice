package thread;

public class SynchronizeMethod {
    private int count = 0;

    //这个方法和add方法等价，都是以当前实例为锁，锁住了非原子操作
    public synchronized void addSyn(int n) {
        count += n;
    }

    public synchronized void decSyn(int n) {
        count -= n;
    }

    public int get() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizeMethod counter1 = new SynchronizeMethod();
        SynchronizeMethod counter2 = new SynchronizeMethod();

        Thread thread1 = new Thread(() -> {
            counter1.addSyn(100);
        });
        Thread thread2 = new Thread(() -> {
            counter1.decSyn(100);
        });
        Thread thread3 = new Thread(() -> {
            counter2.addSyn(10);
        });
        Thread thread4 = new Thread(() -> {
            counter2.decSyn(10);
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("counter1 " + counter1.count);
        System.out.println("counter2 " + counter2.count);

    }
}
