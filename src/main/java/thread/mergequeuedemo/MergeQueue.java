package thread.mergequeuedemo;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MergeQueue {
    private LinkedBlockingQueue<RequestPromise> queue;

    private int capacity;

    public MergeQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    @SneakyThrows
    public Response offer(Request request) {
        RequestPromise requestPromise = new RequestPromise(request);
        boolean enqueue = queue.offer(requestPromise, 100, TimeUnit.MILLISECONDS);
        if (!enqueue) {
            return new Response(false, "系统繁忙");
        }

        requestPromise.await();

        return requestPromise.getResponse();
    }

    public void mergeJob() {
        new Thread(() -> {
            List<RequestPromise> list = new ArrayList<>();
            while (true) {
                while (queue.isEmpty()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int i = 0;
                int sum = 0;
                // 取出请求
                while (i < 100 && !queue.isEmpty()) {
                    list.add(queue.poll());
                    sum += list.get(i).getRequest().getNum();
                    i++;
                }
                if (sum <= DataBase.stock) {
                    System.out.printf("合并库存 %d\n", sum);
                    DataBase.stock -= sum;
                    list.forEach(requestPromise -> {
                        requestPromise.setResponse(new Response(true, "ok"));
                        requestPromise.signal();
                    });
                } else {
                    System.out.println("合并库存扣减失败，服务降级");
                    for (RequestPromise requestPromise : list) {
                        int count = requestPromise.getRequest().getNum();
                        if (count <= DataBase.stock) {
                            DataBase.stock -= count;
                            requestPromise.setResponse(new Response(true, "ok"));
                        } else {
                            requestPromise.setResponse(new Response(false, "failed"));
                        }
                        requestPromise.signal();
                    }
                }
            }
        }, "merger-thread").start();
    }
}
