package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolException {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        addThreadFactoryTest();
//        noThreadFactoryTest();

    }


    // 添加线程工厂
    private static void addThreadFactoryTest() throws InterruptedException, ExecutionException {
        //1.实现一个自己的线程池工厂
        ThreadFactory factory = (Runnable r) -> {
            //创建一个线程
            Thread t = new Thread(r);
            //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
            t.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
                System.out.println("线程工厂设置的exceptionHandler" + e.getMessage());
            });
            return t;
        };

        //2.创建一个自己定义的线程池，使用自己定义的线程工厂
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(10),
                factory);

        // submit无提示
        Future<?> res = executorService.submit(new task());
        // submit想要打印异常信息需要手动get
//        System.out.println(res.get());

        Thread.sleep(1000);
        System.out.println("==================为检验打印结果，1秒后执行execute方法");

        // execute 方法被线程工厂factory 的UncaughtExceptionHandler捕捉到异常
        executorService.execute(new task());

        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);
    }

    // 不添加线程工厂
    private static void noThreadFactoryTest() throws InterruptedException, ExecutionException {
        //创建一个自己定义的线程池，使用自己定义的线程工厂
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(10));

        // submit无提示
        Future<?> res = executorService.submit(new task());

        Thread.sleep(1000);
        System.out.println("==================为检验打印结果，1秒后执行execute方法");
        // submit想要打印异常信息需要手动get
//        System.out.println(res.get());

        // execute 方法被线程工厂factory 的UncaughtExceptionHandler捕捉到异常
        executorService.execute(new task());

        executorService.shutdown();
        executorService.awaitTermination(3, TimeUnit.SECONDS);
    }

}

class task implements Runnable {
    @Override
    public void run() {
        System.out.println("进入了task方法！！！");
        int i = 1 / 0;
    }
}

/* 经过测试不管是否 添加线程工厂进行处理

    execute方法总会抛出异常
    而submit方法不会

    我们看submit的源代码会发现
    submit 会先新建一个futureTask
    然后调用execute执行这个futureTask中的run方法
    我们再去看futureTask中的run方法会发现

    如果顺利进行，没有异常，run方法会调用set方法将结果设置给给outcome
    其中把异常捕获了,并且调用setException方法,把异常放入outcome

    在我们再去看get()方法
    get方法通过判断线程的状态来决定返回值
    如果当前状态小于完成状态
    当前线程就会等待直到线程完成

    如果当前线程的状态大于完成状态（抛出异常时线程的状态会设置成大于完成值） 调用 report方法返回
    report方法中会返回outcome的值
    所以get方法就是返回outcome的值

    因此我们可以通过get方法获得异常信息
 *
 */