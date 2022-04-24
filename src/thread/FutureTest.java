package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 使用future有序的获取返回结果，测试
public class FutureTest {
    public ThreadPoolExecutor es = new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTest futureTest = new FutureTest();
        futureTest.futureTest1();
        futureTest.futureTest2();
        futureTest.NormalTest();
    }

    //测试future，获取返回值
    private void futureTest1() throws InterruptedException, ExecutionException {
        System.out.println("------------使用future购买一个包子和一份面条-----------");
        long start = System.currentTimeMillis();
        Future<String> future1 = es.submit(new MakeBum());
        Future<String> future2 = es.submit(new MakeNoodle());
        Thread.sleep(100);
        /*
         * 将执行需要三秒的放在前面
         * 看看是否为按序
         *
         * 可以试试调换两个输出的顺序后看看
         * 那我们试试做面的同时能否做三份面？
         */
        System.out.println(future2.get());
        System.out.println(future1.get());
        long end = System.currentTimeMillis();

        System.out.println("一份面和一份包子准备完毕时间：" + (end - start) + "ms");
        es.awaitTermination(1, TimeUnit.SECONDS);
    }

    //测试future，获取返回值
    private void futureTest2() throws InterruptedException, ExecutionException {
        System.out.println("------------使用future购买三个包子和一份面条-----------");
        long start = System.currentTimeMillis();
        Future<String> future1 = es.submit(new MakeBum());
        Future<String> future2 = es.submit(new MakeNoodle());
        Future<String> future3 = es.submit(new MakeBum());
        Future<String> future4 = es.submit(new MakeBum());
        Thread.sleep(100);
        /*
         * 测试三个包子
         */
        System.out.println(future2.get());
        System.out.println(future1.get());
        System.out.println(future3.get());
        System.out.println(future4.get());
        long end = System.currentTimeMillis();

        System.out.println("三个包子和一份面条 准备完毕时间：" + (end - start) + "ms");
        es.shutdown();
    }

    /*
     * 这里我们是为了按序获取
     * 所以start完后，马上join
     *
     * 如果不马上join另一个只需要执行1秒的线程会先输出
     *
     * 就不是有序了
     */
    private void NormalTest() throws InterruptedException {
        System.out.println("------------使用普通多线程购买一个包子和一份面条-----------");
        long start = System.currentTimeMillis();

        // 等凉菜 -- 必须要等待返回的结果，所以要调用join方法
        Thread t1 = new ColdDishThread();
        t1.start();
        t1.join();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Thread t2 = new BumThread();
        t2.start();
        t2.join();

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间：" + (end - start));
    }
}

//做面条 3秒
class MakeNoodle implements Callable<String> {
    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(1000 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "面条已经制作完毕 耗时3秒";
    }
}

//做包子 1秒
class MakeBum implements Callable<String> {
    @Override
    public String call() throws Exception {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "包子已经制作完毕 耗时1秒";
    }
}

class BumThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * 3);
            System.out.println("包子准备完毕 耗时3秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class ColdDishThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("凉菜准备完毕 耗时1秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
/**
 * 总结
 * future可以获取返回结果
 * 并且可以保证获取结果的顺序
 */