package thread;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 只允许一个线程写入（其他线程既不能写入也不能读取）
 * 没有写入时，允许多个线程同时读取
 */
public class ReadWriteLockTest {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rlock = rwLock.readLock();
    private final Lock wlock = rwLock.writeLock();

    private final static int READ_THREAD_NUMBER = 10;
    private final static int WHITE_THREAD_NUMBER = 3;
    private int count = 0;

    public void inc() {
        wlock.lock();
        String ThreadName = Thread.currentThread().getName();
        try {
            count++;
            System.out.printf("%s is writing>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n", ThreadName);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("%s unlock the writeLock>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n", ThreadName);
            wlock.unlock();
        }
    }

    /*
        既然是可以多个线程同时读，能否不加读锁？
        如果不加读锁，别的线程写入呢，会导致你读的数据出错
        加读锁，你读的时候别的线程也不能写入
        一般论坛用读写锁
        我们用论坛大部分时间都在读，只有少部分时间在写
     */
    public void getCount() {
        rlock.lock();
        String ThreadName = Thread.currentThread().getName();
        try {
            System.out.printf("%s is reading............ %d\n", ThreadName, count);
        } finally {
            System.out.printf("%s unlock the readLock............\n", ThreadName);
            rlock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        var tl = new ArrayList<Thread>();
        for (int i = 0; i < READ_THREAD_NUMBER ;i++) {
            Thread rthread = new Thread(() -> {
                readWriteLockTest.getCount();
            });
            tl.add(rthread);
        }
        for (int i = 0; i < WHITE_THREAD_NUMBER ; i++) {
            Thread wthread = new Thread(() -> {
                readWriteLockTest.inc();
            });
            tl.add(wthread);
        }
        //使用java Stream并行处理
        tl.stream().parallel().forEach(Thread::start);
    }

}
/*
 * Thread-0 is reading............ 0
 * Thread-10 is reading............ 0
 * Thread-10 unlock the readLock............
 * Thread-3 is reading............ 0
 * Thread-3 unlock the readLock............
 * Thread-9 is reading............ 0
 * Thread-9 unlock the readLock............
 * Thread-6 is reading............ 0
 * Thread-6 unlock the readLock............
 * Thread-5 is reading............ 0
 * Thread-5 unlock the readLock............
 * Thread-2 is reading............ 0
 * Thread-2 unlock the readLock............
 * Thread-1 is reading............ 0
 * Thread-1 unlock the readLock............
 * Thread-0 unlock the readLock............
 * Thread-13 is writing............
 * Thread-13 unlock the writeLock............
 * Thread-14 is writing............
 * Thread-14 unlock the writeLock............
 * Thread-4 is reading............ 2
 * Thread-4 unlock the readLock............
 * Thread-11 is reading............ 2
 * Thread-11 unlock the readLock............
 * Thread-8 is reading............ 2
 * Thread-8 unlock the readLock............
 * Thread-7 is reading............ 2
 * Thread-7 unlock the readLock............
 * Thread-12 is writing............
 * Thread-12 unlock the writeLock............
 */

/*
 * 由于线程的最后的调度受到操作系统内核的控制，所以并不能每一次都获得类似于上述所示的结果
 *
 * 看前面的两行可以发现，线程0和线程10同时在读
 * 也就是说允许多个线程同时读取
 *
 * 看线程14，写线程，我们在写线程中休眠了100ms，但是中间并没有读或者写
 * 也就是说写的过程中不能读也不能写
 *
 *在看后面三个线程的读，发现没有写入操作
 * 也就是说读的过程中也不能写
 *
 * 总结：
 * 1. 只有读的时候可以多个一起读
 * 2. 写的时候，不能读也不能写
 * 3. 读的时候不能写
 *
 * ------------------------那么会存在什么问题-----------------？
 *如果读特别多比如100000个，而写只有1个
 * 那么写线程会存在饥饿问题
 *
 */