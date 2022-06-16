package thread.threadpool.mythreadpool.v2;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

//在v1的基础上实现懒加载
public class MyThreadPool {
    private BlockingQueue<Runnable> taskQueue;
    private List<MyThread> threads;

    private volatile int corePoolSize;

    private AtomicInteger workers;

    public MyThreadPool(BlockingQueue<Runnable> taskQueue, int threadSize) {
        if (threadSize < 0)
            throw new IllegalArgumentException();

        this.taskQueue = taskQueue;
        this.threads = new CopyOnWriteArrayList<>();
        this.corePoolSize = threadSize;
        this.workers = new AtomicInteger(0);
    }

    public void execute(Runnable task) {
        int coreThreadCount = workers.get();
        if (coreThreadCount < corePoolSize) {
            MyThread thread = new MyThread(task, "my thread pool--" + coreThreadCount);
            thread.start();
            threads.add(thread);
            workers.incrementAndGet();
            System.out.println("------------创建新的核心线程-----------------------");
        } else {
            taskQueue.offer(task);
        }
    }

    class MyThread extends Thread {

        private Runnable firstTask;

        public MyThread(Runnable task, String name) {
            super(name);
            firstTask = task;
        }

        @Override
        public void run() {
            if (firstTask != null) {
                firstTask.run();
                firstTask = null;
            }
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
        MyThreadPool threadPool = new MyThreadPool(new ArrayBlockingQueue<>(10), 4);

        IntStream.rangeClosed(1, 30).forEach(i -> {
            try {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " exec task :" + i);
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

