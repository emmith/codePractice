package thread.locktest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    private static final int TASK_NUMBER = 100;
    private static final int THREAD_NUMBER = 4;

    public static void main(String[] args) throws InterruptedException {
        var ts = new TaskQueueReenTrantLock();
        var tl = new ArrayList<Thread>();

        for (int i = 0; i < THREAD_NUMBER; i++) {
            var thread = new Thread(() -> {
                while (true) {
                    try {
                        String threadName = Thread.currentThread().getName();
                        String s = ts.getTask();
                        System.out.printf("%s exec task : %s \n", threadName, s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            tl.add(thread);
        }

        var add = new Thread(() -> {
            for (int i = 0; i < TASK_NUMBER; i++) {
                String task = "task-" + i;
                System.out.printf("add %s \n", task);
                ts.addTask(task);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        long start = System.currentTimeMillis();
        add.start();
        add.join();

        for (var task : tl) {
            task.interrupt();
        }
        //2411ms
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}

class TaskQueueReenTrantLock {
    private final Lock lock = new ReentrantLock();
    Queue<String> queue = new LinkedList<>();
    private final Condition condition = lock.newCondition();

    public void addTask(String s) {
        if (lock.tryLock()) {
            try {
                queue.add(s);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    public String getTask() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
