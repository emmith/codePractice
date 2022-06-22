package thread.synchronize;

import lombok.SneakyThrows;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ProviderConsumerModel {
    public static int capacity = 50;
    public static Queue<Integer> queue = new ArrayDeque<>(capacity);
    public static Object lock = new Object();

    private static int consumerNum = 10;
    private static int providerNum = 5;

    public static void main(String[] args) throws InterruptedException {
        List<Provider> providers = new ArrayList<>();
        for (int i = 0; i < providerNum ;i++) {
            Provider provider = new Provider("生产者" + i);
            provider.start();
            providers.add(provider);
        }

        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < consumerNum ;i++) {
            Consumer consumer = new Consumer("消费者" + i);
            consumer.start();
            consumers.add(consumer);
        }
    }
}

class Provider extends Thread {

    public Provider(String name) {
        super(name);
    }

    @Override
    public void run() {
        IntStream.rangeClosed(0, 20).forEach(i -> {
            add();
        });
    }

    @SneakyThrows
    private void add() {
        Thread.sleep(100);
        synchronized (ProviderConsumerModel.lock) {
            while (ProviderConsumerModel.queue.size() == ProviderConsumerModel.capacity) {
                ProviderConsumerModel.lock.wait();
            }
            int num = ThreadLocalRandom.current().nextInt();
            System.out.printf("%s ---添加 %d\n", this.getName(), num);
            ProviderConsumerModel.queue.offer(num);
            ProviderConsumerModel.lock.notifyAll();
        }
    }
}


class Consumer extends Thread {

    public Consumer(String name) {
        super(name);
    }

    @Override
    public void run() {
        IntStream.rangeClosed(0, 10).forEach(i -> {
            dec();
        });
    }

    @SneakyThrows
    private void dec() {
        Thread.sleep(100);
        synchronized (ProviderConsumerModel.lock) {
            while (ProviderConsumerModel.queue.isEmpty()) {
                ProviderConsumerModel.lock.wait();
            }
            int num = ProviderConsumerModel.queue.poll();
            System.out.printf("%s 消费 %d\n", this.getName(), num);
            ProviderConsumerModel.lock.notifyAll();
        }

    }
}