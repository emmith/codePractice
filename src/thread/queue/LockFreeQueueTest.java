package thread.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class LockFreeQueueTest<E> {

    static class Node<E> {
        E value;
        AtomicReference<Node<E>> next;

        public Node() {
            next = new AtomicReference<>();
        }
    }

    private final AtomicReference<Node<E>> head, tail;

    public LockFreeQueueTest() {
        head = new AtomicReference<>();
        tail = new AtomicReference<>();
        Node<E> node = new Node<>();
        head.set(node);
        tail.set(node);
    }

    public void enqueue(E e) { 
        Node<E> node = new Node<>();
        node.value = e;
        node.next.set(null);
        Node<E> tail;

        while (true) {
            tail = this.tail.get();
            Node<E> next = tail.next.get();

            //如果尾部节点没有被改变
            if (tail == this.tail.get()) {
                if (next == null) {
                    if (tail.next.compareAndSet(null, node)) {
                        //成功
                        break;
                    }
                } else {
                    //tail并不是最后的节点，更新tail
                    this.tail.compareAndSet(tail, next);
                }
            }
        }
        //将tail更新为刚刚插入的值
        this.tail.compareAndSet(tail, node);
    }

    public E dequeue() {
        while (true) {
            Node<E> head = this.head.get();
            Node<E> tail = this.tail.get();
            Node<E> next = head.next.get();

            if (head == this.head.get()) {
                if (head == tail) {
                    System.out.println("当前队列为空");
                    if (next == null) {
                        return null;
                    }
                    this.tail.compareAndSet(tail, next);
                } else {
                    E val = next.value;
                    if (this.head.compareAndSet(head, next)) {
                        next.value = null;
                        return val;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        LockFreeQueueTest<Integer> queue = new LockFreeQueueTest<>();
        AtomicInteger count = new AtomicInteger(0);
        IntStream.rangeClosed(0, 5000).parallel().forEach(
                i -> {
                    if (i % 2 == 0) {
                        queue.enqueue(i);
                    } else {
                        queue.dequeue();
                        count.incrementAndGet();
                    }
                }
        );
        System.out.println(count);
    }
}
