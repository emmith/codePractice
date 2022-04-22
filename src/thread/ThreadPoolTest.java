package thread;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTest t = new ThreadPoolTest();
//        threadPoolExecutorTest(t);
//        fixedThreadPoolTest(t);
//        singleThreadPoolTest(t);
        scheduleThreadPoolTest();
    }


    /*
        看源码发现 核心线程数和最大线程数相等
        线程空闲以后等待0就会关闭
        阻塞队列采用链表

        我们假设一开始有几个长任务占满了核心线程
        后面的任务就会阻塞，加入到阻塞队列，如果数量很多，就会导致内存溢出

        我们再看KeepAliveTime 为0 设计者大概任务线程不会空闲，会一直执行长任务

        固定线程池适用于长作业任务，线程数需求少的场景，比如某些CPU密集型任务
     */
    private static void fixedThreadPoolTest(ThreadPoolTest task) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        handleBaseThreadPoolTest(threadPool, task);
    }

    /*
    看源码容易发现 核心线程数为0 最大线程数为32位的最大整数 线程存活时间60L

    采用Synchronous队列，一个不存元素的队列，只要生产者放入任务，消费者同时就会取走
    也就是说在这个队列里，不存在缓存元素
    思想上：先进先出

    由于没有缓存，所以这个线程池会给每一个任务，创建线程，当然如果前面的线程执行完了，可以复用给后面的任务

    假如处理任务的速度小于添加任务的速度，就会不断创建新的线程
    很容易就把内存撑爆

    由于我们无法限制创建线程的数量，尽量别用这个
     */
    private static void cachedThreadPoolTest(ThreadPoolTest task) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        handleBaseThreadPoolTest(threadPool, task);
    }


    /*
    核心线程数为1 最大线程为1 空闲时间0 采用链表阻塞队列

    由于只有一个线程 还采用链表
    其实和固定线程池差不多，把固定线程池的参数改为1，就是这个线程池了

    存在的问题也是：当任务处理满，任务积压时，容易OOM

    比较适合串行化的场景
     */
    private static void singleThreadPoolTest(ThreadPoolTest task) throws InterruptedException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        handleBaseThreadPoolTest(threadPool, task);
    }

    /*
    计划任务线程池
     */
    private static void scheduleThreadPoolTest() throws InterruptedException {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);
        //延时任务
        threadPool.schedule(() -> {
            System.out.println("Hello world");
        }, 1, TimeUnit.SECONDS );

        /*
        延时几秒开始执行循环任务，固定间隔n秒后，执行下一个
        这里的间隔是，上一个开始时间到下一个开始时间
        测试发现，如果上一个任务没有执行完下一个任务会阻塞
        */
        threadPool.scheduleAtFixedRate(() -> {
            System.out.println("Hello world Bob!");
            for (int i = 0, k = 0; i < 1000000000;i++){
                k  += i * i * i * i % 100000007;
                k  *= i % 100000007;
            }
            /*
            测试抛出异常能否继续执行
            经过测试，出现异常后不会再执行后面的定时任务了
             */
            for (int i = 0; i < 10;i++) {
                System.out.println(10 / i);
            }
        }, 2,100, TimeUnit.MILLISECONDS );

        /*
        延时几秒开始执行循环任务，等上一个任务执行完毕后，间隔n秒，执行下一个
        这里的间隔是上一个的结束时间，到下一个的开始时间
        测试发现，如果上一个任务没有执行完下一个任务会阻塞

        */
        threadPool.scheduleWithFixedDelay(() -> {
            System.out.println("Hello world Alice!");
            for (int i = 0, k = 0; i < 1000000000;i++){
                k  += i * i * i * i % 100000007;
                k  *= i % 100000007;
            }
        }, 2,100, TimeUnit.MILLISECONDS );

        threadPool.awaitTermination(20, TimeUnit.SECONDS);
        threadPool.shutdown();
    }

    private static void handleBaseThreadPoolTest(ExecutorService threadPool, ThreadPoolTest task) throws InterruptedException {
        submitTask(threadPool, task);
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(2L, TimeUnit.SECONDS);
            System.out.println(task.count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void threadPoolExecutorTest(ThreadPoolTest task) throws InterruptedException {
        //阿里开发手册建议 尽量使用ThreadPool建立线程池
        final ThreadPoolExecutor es = new ThreadPoolExecutor(9, 9, 10L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.AbortPolicy());
        submitTask(es, task);
        printEsInfo(es);
        es.shutdown();
        try {
            es.awaitTermination(2L, TimeUnit.SECONDS);
            System.out.println(task.count);
            printEsInfo(es);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void submitTask(ExecutorService es, ThreadPoolTest task) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            es.submit(() -> {
                task.inc(2);
            });
            es.submit(() -> {
                task.dec(2);
            });
        }
    }

    public static void printEsInfo(ThreadPoolExecutor es) {
        System.out.println("当前阻塞队列中含有的任务数 " + es.getQueue().size());
        System.out.println("当前活跃线程数 " + es.getActiveCount());
        System.out.println("执行完毕的任务数 " + es.getCompletedTaskCount());
    }

    public synchronized void inc(int n) {
        for (int i = 0; i < 1000000; i++)
            count += n;
    }

    public synchronized void dec(int n) {
        for (int i = 0; i < 1000000; i++)
            count -= n;
    }
}

/**
 * 总结
 *
 * 固定线程池和单一线程池 都采用链表阻塞队列
 * 固定线程池适用于长作业 CPU密集型 任务少的情况
 * 单一线程池适用于规模不大的任务量串行化的场景
 *
 * 可变线程池 采用了同步队列类似于管道，放进去马上就会被取走 线程上限为整数的最大值
 * 适用于短作业场景
 *
 * 计划任务线程池
 * 适用于定时任务
 * 如果前面的任务超时后面会阻塞
 * 如果前面的任务抛出异常后面的任务不会再执行了
 *
 */