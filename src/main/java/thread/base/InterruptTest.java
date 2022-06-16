package thread.base;

import org.junit.Test;
import utils.ThreadUtil;

/**
 * 什么情况下会抛出InterruptedException？
 * 当线程处于waiting，blocking，time-waiting状态下
 * 被调用interrupt()方法时，会抛出这个异常
 * <p>
 * isInterrupted()是干嘛的?
 * 源码中就是读取线程当前的中断标志 interrupted
 * 这是个volatile变量
 * <p>
 * 调用interrupt()方法会发生什么
 * 将对应线程的interrupted 标志 设置为true
 * <p>
 * 为什么调用interrupt方法将睡眠的线程唤醒后调用isInterrupted()为false?
 * 线程处于睡眠状态，无法处理中断，线程需要要恢复成runnable状态后，才能处理中断异常
 * 我们看sleep方法的注释如下，同理还有wait方法
 * The interrupted status of the current thread is cleared when this exception is thrown
 * 当异常抛出时，中断状态会被复原为false
 * <p>
 * 如果线程处于runnable状态下，调用tt.interrupt()方法会怎么样？
 * 只是改了下中断标记的值，没有什么影响
 */
public class InterruptTest {

    // 线程运行时打断
    @Test
    public void test1() throws InterruptedException {
        // 先将标志位设置为true
        Thread.currentThread().interrupt();

        try {
            ThreadUtil.printThreadInfo("线程开始");
            // sleep完，马上读取到标志位为true，抛出中断异常，重置中断标记
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            ThreadUtil.printThreadInfo("发生中断");
            ThreadUtil.printThreadInfo(String.valueOf(Thread.currentThread().isInterrupted()));
        }


        ThreadUtil.printThreadInfo("主线程over");
    }

    // 线程睡眠时打断
    @Test
    public void test2() throws InterruptedException {
        Thread tt = new Thread(() -> {
            try {
                ThreadUtil.printThreadInfo("线程开始");
                // sleep完，马上读取到标志位为true，抛出中断异常，重置中断标记
                Thread.sleep(1000);
                // 在sleep过程中，发现中断标记为true，醒来并且重置中断位
            } catch (InterruptedException e) {
                ThreadUtil.printThreadInfo("发生中断");
                ThreadUtil.printThreadInfo(String.valueOf(Thread.currentThread().isInterrupted()));
            }
        });
        tt.start();
        tt.interrupt();
    }

    // 测试isInterrupted()
    // isInterrupted()只是读取中断标记
    @Test
    public void test3() throws InterruptedException {
        Thread tt = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 1) {
                if (Thread.currentThread().isInterrupted()) {
                    ThreadUtil.printThreadInfo("向左1m");
                } else
                    ThreadUtil.printThreadInfo("向前1m");
            }
        });
        tt.start();
        tt.interrupt();
    }

    // 测试interrupted()
    // interrupted()会重置中断标记
    @Test
    public void test4() throws InterruptedException {
        Thread tt = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 1) {
                if (Thread.interrupted()) {
                    ThreadUtil.printThreadInfo("向左1m");
                } else
                    ThreadUtil.printThreadInfo("向前1m");
            }
        });
        tt.start();
        tt.interrupt();
    }
}
