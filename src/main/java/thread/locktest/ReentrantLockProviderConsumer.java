package thread.locktest;

import lombok.SneakyThrows;
import utils.ThreadUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockProviderConsumer {

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>(10);
        Thread p1 = new Thread(new Provider(queue), "生产者");
        Thread c1 = new Thread(new Consumer(queue), "消费者-1");
        Thread c2 = new Thread(new Consumer(queue), "消费者-2");

        p1.start();
        c1.start();
        c2.start();
    }

    static class Provider implements Runnable {
        Queue<String> queue;

        public Provider(Queue<String> _queue) {
            queue = _queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                ThreadUtil.randomSleep();
                queue.put(i + "");
            }
        }
    }

    static class Consumer implements Runnable {
        Queue<String> queue;

        public Consumer(Queue<String> _queue) {
            queue = _queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                ThreadUtil.randomSleep();
                queue.take();
            }
        }
    }


    static class Queue<T> {
        private static final Lock lock = new ReentrantLock();
        private static final Condition provCond = lock.newCondition();
        private static final Condition consCond = lock.newCondition();

        int capacity;
        int putIdx = 0;
        int takeIdx = 0;
        int count = 0;
        Object[] ele;

        public Queue(int _capacity) {
            capacity = _capacity;
            ele = new Object[capacity];
        }


        @SneakyThrows
        public void put(T e) {
            lock.lock();
            try {
                while (count == capacity) {
                    //满了，生产者睡觉
                    System.out.println("=====满了，生产者开始睡觉=====");
                    provCond.await();
                }
                System.out.println("生产者开始生产........" + e);
//                    Thread.sleep(100);
                System.out.println("生产者开始生产完成！！！");
                ele[putIdx++] = e;
                count++;

                if (putIdx == capacity) {
                    putIdx = 0;
                }
                //唤醒消费者
                System.out.println("-----通知消费者消费");
                consCond.signalAll();
            } finally {
                lock.unlock();
            }

        }

        @SneakyThrows
        public void take() {
            lock.lock();

            try {
                while (count == 0) {
                    //没有东西可以消费
                    System.out.println("=====没有东西可以消费，消费者开始睡觉=====");
                    consCond.await();
                }
                Object e = ele[takeIdx++];
                System.out.println(Thread.currentThread().getName() + "开始消费.........." + e);
//                    Thread.sleep(100);
                System.out.println("消费者消费完成！！！");

                if (takeIdx == capacity) {
                    takeIdx = 0;
                }
                count--;
                //唤醒生产者
                System.out.println("-----通知生产者生成");
                provCond.signalAll();
            } finally {
                lock.unlock();
            }

        }

    }
}
