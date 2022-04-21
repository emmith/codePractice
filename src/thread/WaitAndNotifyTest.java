package thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WaitAndNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        var ts = new TaskQueue();
        var tl = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
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
            for (int i = 0; i < 10; i++) {
                String task = "task-" + i;
                System.out.printf("add %s \n", task);
                ts.addTask(task);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        add.start();
        add.join();

        for (var task : tl) {
            task.interrupt();
        }

    }
}

class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        //唤醒调用了wait进入等待状态的线程
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        /*
         * 这里为什么要用while循环，而不是if呢
         * 因为notifyAll是一次唤醒所有线程，换成if是直接往下执行
         * 而加入队列已经被其他线程搬空，就会报错
         * 用while是为了再判断一次队列是否为空
         *
         * 为什么不用notify呢，notify是唤醒一个，不能最大的利用资源
         */
        while (this.queue.isEmpty()) {
            //获得锁以后发现没有任务，就会释放锁，进入等待状态，等待notify唤醒
            //被唤醒后需要重新获得锁
            this.wait();
        }

        return queue.remove();
    }
}
