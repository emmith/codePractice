package thread.memorymodel;

import lombok.SneakyThrows;
import org.junit.Test;
import sun.misc.Unsafe;
import utils.ThreadUtil;

import java.lang.reflect.Field;

public class ThreadMemoryModelTest {
    // while死循环，计算密集型，独占CPU
    // 这里子线程已经将flag修改为了true
    // 当前线程并没有检测到，而且非常长的时间没有检测到
    // 符合java内存模型中的规定，当前CPU一直在读取缓存行中的副本
    // 它读取到的flag值，一直都是false
    // 想要避免这种情况，我们需要让CPU清空缓存
    // 1. 将flag设置为volatile变量
    // 2. 使用unsafe类中的内存屏障
    // 3. 让出CPU，让CPU先去执行别的任务，进程切换也能达到清空CPU缓存的目的
    @Test
    public void test1() {
        TestThread test = new TestThread();
        Thread thread = new Thread(test, "subThread");
        thread.start();

        while (true) {
            boolean flag = test.isFlag();
            if (flag) {
                System.out.println("flag changed ,break");
                break;
            }
        }

        System.out.println("main thread end");
    }

    // sleep 让出CPU，达到清空缓存行的目的
    @Test
    public void test2() {
        TestThread test = new TestThread();
        Thread thread = new Thread(test, "subThread");
        thread.start();

        while (true) {
            boolean flag = test.isFlag();
            if (flag) {
                System.out.println("flag changed ,break");
                break;
            }
            ThreadUtil.randomSleep();
        }

        System.out.println("main thread end");
    }

    // 处理IO，不断IO时，cpu中间也可能会切换到别的任务，达到清空缓存的目的
    // 远远没有sleep好用
    @Test
    public void test3() {
        TestThread test = new TestThread();
        Thread thread = new Thread(test, "subThread");
        thread.start();

        while (true) {
            boolean flag = test.isFlag();
            System.out.println("test....." + flag);
            if (flag) {
                System.out.println("flag changed ,break");
                break;
            }
        }

        System.out.println("main thread end");
    }


    // 使用内存屏障
    @Test
    @SneakyThrows
    public void test4() {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        // 将修饰符修改为public，否则无法访问
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        TestThread test = new TestThread();
        Thread thread = new Thread(test, "subThread");
        thread.start();

        while (true) {
            boolean flag = test.isFlag();
            // 加入内存屏障，屏障前的都无法重排序到屏障后
            // 屏障后的读无法重排序到屏障前
            // 具体实现就是清空缓存行，再去内存中读取一次
            unsafe.loadFence();
            if (flag) {
                System.out.println("flag changed ,break");
                break;
            }
        }

        System.out.println("main thread end");
    }
}

class TestThread implements Runnable {
    // volatile
    boolean flag = false;

    @Override
    @SneakyThrows
    public void run() {
        Thread.sleep(2000);
        System.out.printf("%s change flag to true\n", Thread.currentThread().getName());
        flag = true;
    }

    public boolean isFlag() {
        return flag;
    }
}