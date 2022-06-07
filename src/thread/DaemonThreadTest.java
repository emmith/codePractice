package thread;

import org.testng.annotations.Test;
import utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DaemonThreadTest {
    //I am main thread
    //Son thread is over!!
    //I am son thread son thread
    // JVM会在所有非守护线程执行完毕后，退出，这里则是等主线程和子线程执行完成后退出
    @Test
    private static void testNormalThread() {
        System.out.println("----------------------testNormalThread--------------------");
        Thread thread = new Thread(() -> {
            System.out.printf("I am son thread %s\n", Thread.currentThread().getName());
        }, "son thread");

        ThreadUtil.randomSleep();
        System.out.println("Son thread is over!!");

        thread.start();
    }

    //I am main thread
    //Son thread is over!!
    // 这里子线程为守护线程，JVM在主线程执行完成后退出
    // 在JVM退出后？守护线程是否会继续执行？
    // 如果JVM退出了，守护线程也消失了
    // 守护线程的作用是服务其他线程的，就像垃圾收集器，它就是守护线程
    // 如果JVM都退出了，还收集什么垃圾呢，没有垃圾了，收集器就关闭了
    // 通常守护线程都是while死循环，用于轮询监控别的线程状况
    // 它的死循环的退出时机是，没有别的工作线程了，只剩自己这个守护者了，然而需要守护的东西已经没了，它就自杀了
    // 假如我们不用守护线程，用普通线程作为垃圾收集器线程
    // 那while死循环，会一直执行，JVM永远不会退出
    // 另外建议别用守护线程做文件读写等需要close的操作，因为如果出现问题，守护线程可能会一直持有文件描述符，导致别的线程没法写入
    @Test
    private static void testDaemonThread() {
        System.out.println("----------------------testDaemonThread--------------------");
        Thread thread = new Thread(() -> {
            System.out.printf("I am son thread %s\n", Thread.currentThread().getName());
        }, "son thread");
        thread.setDaemon(true);

        ThreadUtil.randomSleep();
        System.out.println("Son thread is over!!");

        thread.start();
    }

    // 测试ThreadPoolExecutor线程池
    // 线程池不管是核心线程还是队列满了之后创建的新的线程
    // 都是普通线程
    // JVM会在主线程和线程池执行完成后关闭
    //I am main thread
    //main thread is start
    //main thread is over
    //Pool son Thread pool-1-thread-1
    //Pool son Thread is over
    @Test
    private static void testThreadPoolThread() {
        ThreadPoolExecutor es = new ThreadPoolExecutor(4, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        System.out.println("main thread is start");

        es.submit(() -> {
            System.out.printf("Pool son Thread %s\n", Thread.currentThread().getName());
            System.out.println("Pool son Thread is over");
        });

        es.shutdown();
        System.out.println("main thread is over");
    }

    // 测试ForkJoin线程池
    // ForkJoin中的线程为守护线程
    // 主线程执行完成后，JVM就关闭了，forkJoin线程池中的任务也就不执行了
    // 想要forkJoin线程池中的线程执行完成，可以让主线程睡眠几秒
    // I am main thread
    //main thread is start
    //main thread is over
    @Test
    private static void testForkJoinPoolThread() throws InterruptedException {
        System.out.println("main thread is start");
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.execute(() -> {
            System.out.printf("Pool son Thread %s\n", Thread.currentThread().getName());
            System.out.println("Pool son Thread is over");
        });
//        forkJoinPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        forkJoinPool.shutdown();
        System.out.println("main thread is over");
    }

    //I am main thread
    //main thread is start
    //Pool son Thread ForkJoinPool.commonPool-worker-1
    //main thread is over
    // CompletableFuture的默认线程池为 ForkJoin线程池
    // 为了能让CompletableFuture任务执行完，我们得让主线程等待其执行完
    @Test
    private static void testCompletableFuture() {
        System.out.println("main thread is start");

        CompletableFuture test1 = CompletableFuture.runAsync(() -> {
            System.out.printf("Pool son Thread %s\n", Thread.currentThread().getName());
            System.out.printf("Pool son Thread %s is working\n", Thread.currentThread().getName());
        }).whenComplete((res, th) -> {
            System.out.printf("Pool son Thread is over!!%s\n", Thread.currentThread().getName());
        });

        CompletableFuture test2 = CompletableFuture.runAsync(() -> {
            System.out.printf("Pool son Thread %s\n", Thread.currentThread().getName());
            System.out.printf("Pool son Thread %s is working\n", Thread.currentThread().getName());
        }).whenComplete((res, th) -> {
            System.out.printf("Pool son Thread is over!!%s\n", Thread.currentThread().getName());
        });

        test1.join();
        test2.join();
        System.out.println("main thread is over");
    }
}
