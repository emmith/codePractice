package thread.threadpool.mythreadpool.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class MyThreadPool {
    BlockingQueue<Runnable> taskQueue;
    List<MyThread> threads;

    public MyThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize) {
        this.taskQueue = taskQueue;
        this.threads = new ArrayList<>(threadSize);

        // 初始化工作线程
        IntStream.rangeClosed(1, threadSize)
                .forEach( i -> {
                    MyThread thread = new MyThread("my-thread-pool-" + i);
                    thread.start();
                    threads.add(thread);
                });
    }

    public void execute(Runnable task) {
        taskQueue.offer(task);
    }

    class MyThread extends Thread {

        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            // 不断的取出任务
            while (true) {
                Runnable task = null;
                try {
                    task = taskQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                task.run();
            }
        }
    }

    public static void main(String[] args) {
        MyThreadPool threadPool = new MyThreadPool(new ArrayBlockingQueue<>(10), 3);

        IntStream.rangeClosed(1, 10).forEach( i -> {
            try {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() +" exec task :" + i);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

