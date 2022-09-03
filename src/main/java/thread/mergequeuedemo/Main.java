package thread.mergequeuedemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static int requestNum = 5500;

    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(12, 20, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 用线程来模拟用户
        ExecutorService threadPool = Executors.newCachedThreadPool();
        MergeQueue mq = new MergeQueue(500);
        mq.mergeJob();

        List<Future<Response>> futures = new ArrayList<>();
        for (int i = 0; i < requestNum; i++) {
            final Long orderId = 10L + i;
            final Long skuId = 2L;

            Future<Response> responseFuture = threadPool.submit(() -> {
                return mq.offer(new Request(orderId, skuId, 1));
            });
            futures.add(responseFuture);
        }

        futures.forEach(res -> {
            try {
                Response responses = res.get(500, TimeUnit.MILLISECONDS);
                System.out.printf("客户端响应 %s\n", responses);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(2000);
        System.out.println(DataBase.stock);
    }
}
