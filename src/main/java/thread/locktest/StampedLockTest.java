package thread.locktest;

import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    private final StampedLock stampedLock = new StampedLock();
    private final static int READ_THREAD_NUMBER = 10;
    private final static int WHITE_THREAD_NUMBER = 2;
    private int X = 0;
    private int Y = 0;

    public void assign(int x, int y) {
        long stamp = stampedLock.writeLock();
        try {
            X = x;
            Y = y;
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    /*
    可以发现这里和读写锁的区别是
    读的时候不上锁，我们在代码中只获得了一个版本号，没有做处理
    如果该版本是最新版本，我们直接返回了，没有上锁的资源消耗

    如果不是最新版本，我们才会锁

    相比读写锁
    1. 读的时候基本上没有锁
    2. 读的时候可以写

     */
    public int getArea() {
        long stamp = stampedLock.tryOptimisticRead();
        String threadName = Thread.currentThread().getName();
        //获取x和y
        int curX = X;
        int curY = Y;
        System.out.printf("%s Read current X : %d, current Y : %d\n", threadName, curX, curY);

        //检查版本号，如果该版本后又进行了写入
        if (!stampedLock.validate(stamp)) {
            //申请一个悲观读锁，也就是读的时候不许写
            stamp = stampedLock.readLock();
            try {
                curX = X;
                curY = Y;
                System.out.printf("%s\n !!!!data has been modified\nRead the newest X : %d, the newest Y : %d\n",threadName, curX, curY);
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        int area = curX * curY;
        System.out.printf("%s Current area %d\n", threadName, area);
        return area;
    }

    public static void main(String[] args) {
        List<Thread> tl = new ArrayList<Thread>();

        StampedLockTest stampedLockTest = new StampedLockTest();
        //添加读线程
        for (int i = 0; i < READ_THREAD_NUMBER ;i++) {
            Thread rthread = new Thread(() -> {
                stampedLockTest.getArea();
            });
            tl.add(rthread);
        }

        //添加读线程
        for (int i = 0; i < WHITE_THREAD_NUMBER ;i++) {
            Thread wthread = new Thread(() -> {
                int x = RandomUtil.randomInt(1, 100);
                int y = RandomUtil.randomInt(1, 100);
                stampedLockTest.assign(x, y);
            });
            tl.add(wthread);
        }

        tl.stream().parallel().forEach(Thread::start);
    }
}

/*
Thread-7 Read current X : 0, current Y : 0
Thread-4 Read current X : 16, current Y : 20
Thread-0 Read current X : 16, current Y : 20
Thread-0 Current area 320
Thread-2 Read current X : 16, current Y : 20
Thread-2 Current area 320
Thread-5 Read current X : 16, current Y : 20
Thread-1 Read current X : 16, current Y : 20
Thread-1 Current area 320
Thread-3 Read current X : 16, current Y : 20
Thread-3 Current area 320
Thread-9 Read current X : 0, current Y : 0
Thread-9
 !!!!data has been modified
Read the newest X : 16, the newest Y : 20
Thread-9 Current area 320
Thread-6 Read current X : 0, current Y : 0
Thread-6
 !!!!data has been modified
Read the newest X : 16, the newest Y : 20
Thread-8 Read current X : 0, current Y : 0
Thread-6 Current area 320
Thread-5 Current area 320
Thread-4 Current area 320
Thread-7
 !!!!data has been modified
Read the newest X : 16, the newest Y : 20
Thread-8
 !!!!data has been modified
Read the newest X : 16, the newest Y : 20
Thread-8 Current area 320
Thread-7 Current area 320
 */

/*
上面是其中一次运行结果
其中只有四次上了读锁，而我们之前的读写锁需要上十次读锁


这就是所谓的乐观锁

乐观锁你甚至可以认为没有锁
只需要检查一下版本号，如果版本号是最新的
直接读，不是最新我们再考虑上读锁

上读锁就是为了更新到最新的数据

我们可以联想到 git的版本管理 淘宝购物车等，都是乐观锁的实现
 */