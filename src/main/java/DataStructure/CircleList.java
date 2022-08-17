package DataStructure;

public class CircleList {

    Node head;

    Node tail;

    class Node {
        int value;

        Node prev;

        Node next;

        public Node() {
        }

        public Node(int v) {
            value = v;
        }
    }

    public CircleList() {
        head = new Node();
        tail = new Node();
        head.prev = tail;
        head.next = tail;
        tail.prev = head;
        tail.next = head;
    }

    public boolean isEmpty() {
        return head.next == tail;
    }

    public void insertFirst(int value) {
        Node node = new Node(value);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;

        print("插入首部 " + value);
    }

    public void insertLast(int value) {
        Node node = new Node(value);
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        print("插入尾部 " + value);
    }

    public void deleteFirst() {
        if (isEmpty()) return;
        head.next.next.prev = head;
        head.next = head.next.next;
        print("删除首部");
    }

    public void deleteLast() {
        if (isEmpty()) return;
        tail.prev.prev.next = tail;
        tail.prev = tail.prev.prev;
        print("删除尾部");
    }

    public void print(String msg) {
        Node h = head.next;
        System.out.printf("%s ---", msg);
        while (h != tail) {
            System.out.printf("%d ", h.value);
            h = h.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CircleList main = new CircleList();
        for (int i = 0; i < 10; i++) {
            if ((i & 1) == 1) main.insertFirst(i);
            else main.insertLast(i);
        }

        for (int i = 0; i < 10; i++) {
            if (Math.random() < 0.4) main.deleteFirst();
            else main.deleteLast();
        }
    }
}
