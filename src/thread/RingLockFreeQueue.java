package thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.IntStream;

public class RingLockFreeQueue<E> {
    private AtomicReferenceArray atomicArray;
    AtomicInteger head, tail;

    public RingLockFreeQueue(int capacity) {
        atomicArray = new AtomicReferenceArray<E>((E[]) new Object[capacity + 1]);
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
    }

    public boolean enqueue(E e) {
        int index = (tail.get() + 1) % atomicArray.length();

        if (index == head.get() % atomicArray.length()) {
            System.out.println("队列已经满了，无法入队");
            return false;
        }

        //不断尝试对tail的后一个节点赋值
        while (!atomicArray.compareAndSet(index, null, e)) {
            return enqueue(e);
        }

        this.tail.incrementAndGet();
        System.out.println("入队成功");
        return true;
    }

    public E dequeue() {
        if (head.get() == tail.get()) {
            System.out.println("队列为空");
            return null;
        }
        int index = (head.get() + 1) % atomicArray.length();
        E e = (E) atomicArray.get(index);

        //可能其他线程在出队列
        if (e == null) {
            return dequeue();
        }

        //不断尝试将要出队的节点位置赋空
        while (!atomicArray.compareAndSet(index, e, null)) {
            return dequeue();
        }

        head.incrementAndGet();
        System.out.println("出队成功");
        return e;
    }

    public void print() {
        for (int i = head.get() + 1; i < tail.get(); i++) {
            System.out.println(atomicArray.get(i % atomicArray.length()));
        }
    }

    public static void main(String[] args) {
        RingLockFreeQueue<String> queue = new RingLockFreeQueue<>(10);

        IntStream.rangeClosed(0, 1000).parallel().forEach(
                i -> {
                    if (i % 2 == 0) {
                        queue.enqueue("" + i);
                    } else {
                        System.out.println(queue.dequeue());
                    }
                }
        );
        System.out.println();
        queue.print();
    }
}
