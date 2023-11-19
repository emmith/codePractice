package thread.future;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import org.junit.Test;
import utils.ThreadUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static utils.ThreadUtil.randomSleep;
import static utils.ThreadUtil.rm;


/**
 * 这里我们采用的是CompletableFuture.completedFuture("emmith")
 * 这种预置了结果了方式
 *
 *
 */
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
    public void runAsyncExample() {
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
     * 有返回值的方式
     *
     * thenApply thenAccept 和前一步用的用一个线程
     */
    @Test
    public void supplyAsyncExample() {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            randomSleep();
            ThreadUtil.printThreadInfo("Hello World!");
            return "Hello World!";
        }).thenApply((res) -> {
            randomSleep();
            ThreadUtil.printThreadInfo("Hello World!" + "!!!");
            return res + "!!!";
        });

        cf.thenAccept((result) -> {
            ThreadUtil.printThreadInfo("上一步的返回结果 " + result);
        });

        //不加join，CompletableFuture的默认线程池会马上关闭,可能不会显示上面的输出
        cf.join();
    }

    /**
     * 有返回值的方式
     *
     * 使用自定义线程池 设置普通线程闲置后等待10秒关闭
     */
    @Test
    public void supplyAsyncExample1() throws InterruptedException {
        ThreadPoolExecutor es = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            randomSleep();
            return "Hello World!";
        }, es).thenApply((res) -> {
            randomSleep();
            return res + "!!!";
        });

        cf.thenAccept((result) -> {
            System.out.println("上一步的返回结果 " + result);
        });

        es.shutdown();
        es.awaitTermination(3, TimeUnit.SECONDS);
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
    public void thenApplyExample() {
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
    public void thenAcceptExample() {
        List<String> list= ListUtil.of("Hello", "World");
        StringBuilder res = new StringBuilder();

        list.stream().forEach(i -> {
            CompletableFuture.completedFuture(i).thenAccept( s -> {
                res.append(s);
            });
        });
        System.out.println(res);
    }

    // thenAccept的异步方式
    // 理论上也会用新的线程执行，看线程空闲状况
    @Test
    public void thenAcceptAsyncExample() {
        List<String> list= ListUtil.of("1", "2", "3", "4");

        list.stream().forEach(i -> {
            CompletableFuture.supplyAsync(() -> {
                ThreadUtil.printThreadInfo("supply" + i);
                return i;
            }).thenAcceptAsync( s -> {
                ThreadUtil.printThreadInfo("accept" + s);
            });
        });
        ThreadUtil.millsSleep(200);
    }

    /**
     * thenApply的异步方法
     * thenApplyAsync
     *
     * 理论上thenApplyAsync和前面不是一个线程
     * 不过如果线程上一个线程马上空闲了
     * 可能相同
     */
    @Test
    public void thenApplyAsyncExample() throws InterruptedException {
        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("服务员结账");
            return "hello";
        }).thenApplyAsync((v) -> {
            //判断当前是否为守护线程
            assertTrue(Thread.currentThread().isDaemon());
            ThreadUtil.printThreadInfo("另一个服务员开发票");
            return v;
        });

        cf.join();
    }


    /**
     * 只有有一个执行完就会返回
     *
     * anyOf的返回值为 Object
     */
    @Test
    static void anyOfExample() throws InterruptedException {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture res = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenCompleteAsync((v, th) -> {
            if (th == null) {
                assertTrue((isUpperCase((String) v)));
                result.append(v);
            }
        });
        System.out.println(res.join() + "   "+ result);
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * allOf 没有返回值
     * 为void类型
     * CompletableFuture<void>
     *
     *
     *  所有的按顺序执行完成，才返回1557ms
     *  下面的执行顺序为
     *  a -> b -> c -> d -> finish
     *  为串行化的顺序
     *
     *  因此我们将其用于串行化任务
     *
     */
    @Test
    static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> {
                assertTrue(isUpperCase(cf.getNow(null)));
                result.append(cf.getNow(null));
            });
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
        System.out.println(result);
    }

    /**
     * 所有的按异步执行完成后，返回 647ms
     *
     *  下面的执行顺序为
     *   a ->
     *   b ->
     *   c ->
     *   d ->
     *   并行执行，时间取其中执行时间最长的线程
     *
     *   结果依然按顺序 a b c d保存
     *
     *   我们将其用于批量发请求
     */
    @Test
    static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenCompleteAsync((v, th) -> {
            futures.forEach(cf -> {
                assertTrue(isUpperCase(cf.getNow(null)));
                result.append(cf.getNow(null));
            });
            result.append("done");
        }).join();
        assertTrue("Result was empty", result.length() > 0);
        System.out.println(result);
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

    /**
     * thenCompose 前一个任务完成后
     * 才交给后一个任务
     * 后一个任务supplyAsync
     * 表示又交给了另一个线程服务员
     *
     * 这里我们是假设饭已经做好了
     */
    @Test
    public void testThenCompose() {
        ThreadUtil.printThreadInfo("小王进入餐厅");
        ThreadUtil.printThreadInfo("小王点菜-番茄炒蛋");

        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("厨师做菜");
            ThreadUtil.millsSleep(200);
            ThreadUtil.printThreadInfo("制作番茄炒蛋完成......");
            return "美味的番茄炒蛋";
        }).thenCompose(dist -> CompletableFuture.supplyAsync(() ->{
            ThreadUtil.printThreadInfo("服务员打饭");
            ThreadUtil.millsSleep(100);
            return "米饭" + dist;
        }));

        ThreadUtil.printThreadInfo("小王开始打王者");
        ThreadUtil.printThreadInfo(String.format("小王开始吃 %s", cf.join()));
    }

    /**
     * thenCompose 前一个任务完成后
     * 才交给后一个任务
     * 后一个任务supplyAsync
     * 表示又交给了另一个线程服务员
     *
     * 这里我们的饭没做好
     * 服务员和厨师同时进行
     * 服务员做饭
     * 厨师炒菜
     */
    @Test
    public void testThenCombine() {
        ThreadUtil.printThreadInfo("小王进入餐厅");
        ThreadUtil.printThreadInfo("小王点菜-番茄炒蛋");

        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("厨师做菜");
            ThreadUtil.millsSleep(200);
            ThreadUtil.printThreadInfo("厨师制作番茄炒蛋完成......");
            return "美味的番茄炒蛋";
        }).thenCombine(CompletableFuture.supplyAsync(() ->{
            ThreadUtil.printThreadInfo("服务员做饭");
            ThreadUtil.millsSleep(200);
            return "米饭";
        }), (dist, rice) -> {
            ThreadUtil.printThreadInfo("服务员打饭");
            ThreadUtil.millsSleep(100);
            return dist + rice;
        });

        ThreadUtil.printThreadInfo("小王开始打王者");
        ThreadUtil.printThreadInfo(String.format("小王开始吃 %s", cf.join()));
    }

    /**
     * 两个任务，有一个结束了，就结束任务
     */
    @Test
    public void testApplyEither() {
        ThreadUtil.printThreadInfo("小王正在公交车站等公交车");
        ThreadUtil.printThreadInfo("等待 999 or 888 路公交到来");

        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("999正在路上....");
            randomSleep();
            return "999";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("888正在路上....");
            randomSleep();
            return "888";
        }), first -> first);

        ThreadUtil.printThreadInfo(String.format("小王上%s车了", cf.join()));
    }

    /**
     * 两个任务，有一个结束了，就结束任务
     *
     * 用一个新的线程执行applyToEitherAsync
     */
    @Test
    public void testApplyEitherAsync() {
        ThreadUtil.printThreadInfo("小王给小美打电话");

        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("正在通话连线中....");
            ThreadUtil.millsSleep(700);
            return "电话未接通";
        }).applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            randomSleep();
            ThreadUtil.printThreadInfo("小美接通了电话....");
            return "正在和小美通话";
        }), first -> first);

        ThreadUtil.printThreadInfo(String.format("%s", cf.join()));
    }

    @Test
    public void testHandleException() {
        ThreadUtil.printThreadInfo("小王给小美打电话");

        CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printThreadInfo("正在通话连线中....");
            ThreadUtil.millsSleep(700);
            return "电话未接通";
        }).applyToEitherAsync(CompletableFuture.supplyAsync(() -> {
            randomSleep();
            ThreadUtil.printThreadInfo("小美接通了电话....");
            return "正在和小美通话";
        }), first -> {
            ThreadUtil.printThreadInfo(first);
            if (3 < rm.nextInt(10))
                throw new RuntimeException("小王手机没电了，自动关机");
            return first;
        }).exceptionally(e -> {
            ThreadUtil.printThreadInfo(e.getMessage());
            ThreadUtil.printThreadInfo("小王用座机打电话给小美");
            return "小王用座机给小美打电话";
        });

        ThreadUtil.printThreadInfo(String.format("%s", cf.join()));
    }

}
