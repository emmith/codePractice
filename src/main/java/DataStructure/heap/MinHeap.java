package DataStructure.heap;

import utils.PrintUtil;


public class MinHeap {
    private final int DEFAULT_CAPACITY = 11;

    int size;
    int capacity;
    int[] element;

    public MinHeap() {
        size = 0;
        capacity = DEFAULT_CAPACITY;
        element = new int[capacity];
    }

    public MinHeap(int _capacity) {
        size = 0;
        capacity = _capacity;
        element = new int[capacity];
    }

    //入堆
    public void push(int e) {
        int i = size;
        if (size >= capacity) {
            System.out.println("堆元素已满");
            return;
        }
        adjustUp(i, e);
        size++;
    }

    //出堆
    public int pop() {
        int last = element[size - 1];
        int peek = peek();
        //移除末尾节点
        element[size - 1] = 0;

        size--;
        adjustDown(0, last);
        return peek;
    }

    public int peek() {
        return element[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //插入元素，向上调整
    private void adjustUp(int i, int e) {
        while (i > 0) {
            int parent = (i - 1) >>> 1;
            int parentE = element[parent];
            if (e >= parentE) {
                break;
            }
            element[i] = parentE;
            i = parent;
        }
        element[i] = e;
    }

    private void adjustDown(int i, int e) {
        int half = size >>> 1;
        while (i < half) {
            int left = (i << 1) + 1;
            int right = left + 1;
            //选择左右孩子中小的一个
            if (right < size && element[right] < element[left]) {
                left = right;
            }
            if (e <= element[left]) {
                break;
            }
            element[i] = element[left];
            i = left;
        }
        element[i] = e;
    }

    public void print() {
        PrintUtil.printArray(element);
    }

    public static void main(String[] args) {
        int[] nums = {5,1,1,2,0,0};
        MinHeap minHeap = new MinHeap(nums.length);
        int n = 0;
        while (n < nums.length) {
            minHeap.push(nums[n++]);
        }
        minHeap.print();
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.pop() + " ");
        }
    }
}
