package thread.future;


import org.junit.Test;
import utils.ThreadUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompleteFuturePractice {

    public final List<String> mailList = List.of("""
            xiaoming@gmail.com""", """
            lihua@gmail.com""", """
            zhongli@gmail.com""", """
            xiao@gmail.com""", """
            ganyu@gmail.com""", """
            wendi@gmail.com""", """
            keqing@gmail.com""", """
            ningguang@gmail.com""", """
            qiqi@gmail.com""");

    /*
    场景1:
    同时搜索多个网站
    选择第一个返回的结果
     */
    @Test
    private void practice1() throws InterruptedException {
        CompletableFuture baidu = CompletableFuture.supplyAsync(() -> {
            return search("bupt", """
                    https://www.baidu.com""");
        });

        CompletableFuture google = CompletableFuture.supplyAsync(() -> {
            return search("bupt", """
                    https://www.google.com""");
        });


        CompletableFuture res = CompletableFuture.anyOf(baidu, google);

        /*
         * anyOf有多种方式拿返回值，第一种可以在后面再加一层
         * 比如thenAccept
         *
         * 当主线程执行完后，默认线程池会关闭，所以为了让我们看到thenAccept的结果，让主线程不要太快关闭
         *
         * 还可以用res.join()
         */
        while (!res.isDone()) {
            Thread.sleep(100);
        }
        res.thenAccept((result) -> {
            System.out.println(result);
        });

        /*第二种，调用join可以不需要上面的睡眠时间
            join方法内部调用了waitGet方法
            如果没有获取到值，会一直尝试直到获取到值
         */
//        System.out.println(res.join());

    }


    private String search(String word, String url) {
        System.out.println("search from url :" + url);
        ThreadUtil.randomSleep();
        int resCount = new Random().nextInt(100);
        return url + " 搜索到" + resCount + "个结果";
    }


    /*
    场景2:
    同时发送邮件
    全部发送完成后返回

    注意这个不能保证返回顺序

     */
    @Test
    private void practice2() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor es = new ThreadPoolExecutor(4, 8, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500));
        String msg = "Hello!! ";
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();

        List<CompletableFuture<String>> futures = mailList.stream()
                .map(ele -> {
                    return CompletableFuture.supplyAsync(() -> {
                        return sendMail(msg, ele);
                    }, es).whenCompleteAsync((v, th) -> {
                        if (th == null)
                            list.add(v);
                    });
                }).collect(Collectors.toList());

        CompletableFuture res = CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));

        //等到线程执行完，才输出
        while (!res.isDone()) {
            Thread.sleep(1000);
        }
        System.out.println(list.size() + "" + list);
    }

    private String sendMail(String msg, String mailUrl) {
        System.out.println("Sending mail to " + mailUrl + "...........");
        ThreadUtil.randomSleep();
        return "发送:" + msg + "到" + mailUrl + "成功";
    }
}

/*
 *总结：
 * allOf没有返回值(返回值为void类型)
 * 要拿到返回值可以在后面再加一层
 * 或者定义一个列表存储结果
 *
 * 可以用于发送群消息等场景
 *
 * anyOf 返回最快的线程的返回值
 *
 * 可以用于搜索
 */