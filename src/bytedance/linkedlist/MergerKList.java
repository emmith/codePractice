package bytedance.linkedlist;

import DataStructure.ListNode;

public class MergerKList {

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(-5000);
        ListNode res = head;
        MinHeap minHeap = new MinHeap(lists.length);

        for (ListNode node: lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            head.next = node;
            head = head.next;
            node = node.next;
            minHeap.offer(node);
        }
        head.next = null;
        return res.next;
    }
    public void test () {
        MinHeap minHeap = new MinHeap(10);

        minHeap.offer(new ListNode(7));
        minHeap.offer(new ListNode(6));
        minHeap.offer(new ListNode(5));
        minHeap.offer(new ListNode(4));
        minHeap.offer(new ListNode(3));
        minHeap.offer(new ListNode(2));
        minHeap.offer(new ListNode(1));

        minHeap.print();;


    }
}

class MinHeap {
    int size;
    int capacity;
    ListNode []data;

    public MinHeap(int _capacity) {
        size = 0;
        capacity = _capacity;
        data = new ListNode[capacity];
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    //插入元素
    public boolean offer(ListNode node) {
        if (node == null) {
            return false;
        }
        int i = size;
        if (i < capacity) {
            adjustUp(i, node, data);
            size++;
            return true;
        }
        return false;
    }

    //向上调整
    void adjustUp(int i, ListNode node, ListNode [] es) {
        while (i > 0) {
            int parent = (i-1) >>> 1;
            ListNode parentE = es[parent];
            //父节点小于当前节点，不需要向上调整
            if (node.val >= parentE.val) {
                break;
            }
            //向上调整
            es[i] = parentE;
            i = parent;
        }
        //找到最终调整位置，赋值
        es[i] = node;
    }

    public ListNode peek() {
        return data[0];
    }

    //移除堆顶
    public ListNode poll() {
        final int n = --size;
        ListNode peek = peek();
        ListNode last = data[n];
        //移除末尾
        data[n] = null;
        if (n > 0) {
            adjustDown(0, last, data, n);
        }
        return peek;
    }

    //向下调整
    void adjustDown(int i, ListNode node, ListNode []es, int _size) {
        int half = _size >>> 1;
        while (i < half) {
            int left = (i << 1) + 1;
            int right = left + 1;
            ListNode e = es[left];
            //右边节点更小
            if (right < _size && e.val > es[right].val) {
                e = es[left = right];
            }
            //节点比左右孩子都小，直接终止
            if (node.val <= e.val) {
                break;
            }
            //向下置换
            es[i] = e;
            i = left;
        }
        //找到最终位置，赋值
        es[i] = node;
    }

    public void print() {
        for (int i = 0;i < size;i++) {
            if (data[i] != null)
                System.out.print(data[i].val + " ");
        }
        System.out.println();
    }
}