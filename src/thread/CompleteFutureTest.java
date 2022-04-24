package thread;


import org.testng.annotations.Test;
import utils.ThreadUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Character.isUpperCase;
import static org.testng.Assert.*;
import static utils.ThreadUtil.randomSleep;


public class CompleteFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
    }


    /**
     * 创建一个异步任务
     * 它会默认使用ForkJoinPool
     * <p>
     * 使用守护线程执行  任务
     * <p>
     * runAsync参数为Runnable 没有返回值
     */
    @Test
    void runAsyncExample() {
        //不指定线程池，默认使用ForkJoinPool
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            System.out.println(Math.random());
            assertTrue(Thread.currentThread().isDaemon());
        });

        //指定一个线程池
        ThreadPoolExecutor es = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture cf2 = CompletableFuture.runAsync(() -> {
            System.out.println(Math.random());
            assertTrue(Thread.currentThread().isDaemon());
        }, es);

        es.shutdown();
    }

    /**
     * CompletableFuture.completedFuture("emmith")
     * 会创建一个预定义结果为("emmith")的completedFuture
     * <p>
     * thenApply可以将前面的结果作为参数使用
     * 可以用于链式调用
     *
     * thenApply为同步方法
     * 如果当前没有执行完后面的会阻塞
     */
    @Test
    void thenApplyExample() {
        CompletableFuture cf = CompletableFuture.completedFuture("emmith").thenApply(s -> {
            //判断当前是否为守护线程
            assertFalse(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        });

        //大写操作完成后，才能获得返回值
        assertEquals("EMMITH", cf.getNow(null));
    }

    // 如果不需要返回值，可以使用thenAccept
    @Test
    void thenAcceptExample() {
        List<String> list= List.of("Hello", "World");
        StringBuilder res = new StringBuilder();

        list.stream().forEach(i -> {
            CompletableFuture.completedFuture(i).thenAccept( s -> {
                res.append(s);
            });
        });
        System.out.println(res);
    }

    // thenAccept的异步方式
    @Test
    void thenAcceptAsyncExample() {
        List<String> list= List.of("Hello", "World");
        StringBuilder res = new StringBuilder();

        list.stream().forEach(i -> {
            CompletableFuture.completedFuture(i).thenAcceptAsync( s -> {
                res.append(s);
            }).join();
        });
        System.out.println(res);
    }

    /**
     * thenApply的异步方法
     * thenApplyAsync
     *
     * 用join来获取返回值
     * join等线程执行完成
     * 要不然会等于空值
     */
    @Test
    void thenApplyAsyncExample() throws InterruptedException {
        CompletableFuture cf = CompletableFuture.completedFuture("emmith").thenApplyAsync(s -> {
            //判断当前是否为守护线程
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        });

        assertNull(cf.getNow(null));
        //大写操作完成后，异步用join获取返回值
        assertEquals("EMMITH", cf.join());
    }


    //所有的按顺序执行完成，才返回7599ms
    @Test
    static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if(th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue(result.length() > 0, "Result was empty");
    }

    //所有的按顺序执行完成，才返回7457ms
    @Test
    static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "a", "b", "c", "d", "e", "f", "g");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> {
                assertTrue(isUpperCase(cf.getNow(null)));
            });
            result.append("done");
        });
        assertTrue(result.length() > 0, "Result was empty");
    }

    //所有的按异步执行完成后，返回 1547ms
    @Test
    static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "a", "b", "c", "d", "e", "f", "g");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> {
                assertTrue(isUpperCase(cf.getNow(null)));
            });
            result.append("done");
        }).join();
        assertTrue(result.length() > 0, "Result was empty");
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
