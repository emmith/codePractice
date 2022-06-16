package thread.synchronize;


public class ThreadSynchronize {
    //volatile只能保证线程快速将值写回内存，并不能保证原子性，因为没有办法保证其他线程什么时候读取内存
    //这里是否采用volatile，都不能保证正确性
    public static volatile int count = 0;

    //锁，synchronize需要的参数
    public static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        test2();
    }

    public static void test1() throws InterruptedException {
        myThread1 thread1 = new myThread1();
        myThread2 thread2 = new myThread2();

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(count);
    }

    public static void test2() throws InterruptedException {
        myThread3 thread1 = new myThread3();
        myThread4 thread2 = new myThread4();

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(count);
    }

}

/**
 * 不加锁
 */
class myThread1 extends Thread{
    public void run() {
        for (int i = 0;i < 10000;i++) {
            ThreadSynchronize.count++;
        }
    }
}

class myThread2 extends Thread{
    public void run() {
        for (int i = 0;i < 10000;i++) {
            ThreadSynchronize.count--;
        }
    }
}

/**
 * 加锁
 * 加了锁以后，线程需要获得锁才能对变量进行修改
 *
 * 注意！！！
 * 加上synchronize表示需要获得这把锁，才能往下执行
 * 如果有线程获得了锁，其他线程需要等这个线程释放锁，才能执行代码
 * 所以可以保证原子性
 */
class myThread3 extends Thread{
    public void run() {
        for (int i = 0;i < 10000;i++) {
            //synchronize代表加锁
            synchronized (ThreadSynchronize.lock) {
                ThreadSynchronize.count++;
            }
            //释放锁
        }
    }
}

class myThread4 extends Thread{
    public void run() {
        for (int i = 0;i < 10000;i++) {
            synchronized (ThreadSynchronize.lock) {
                ThreadSynchronize.count--;
            }
        }
    }
}