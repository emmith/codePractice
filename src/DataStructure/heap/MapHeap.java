package DataStructure.heap;


import utils.PrintUtil;

//大根堆
public class MapHeap {
    int capacity;
    int[] heap;
    int size;

    public void printHeapElement() {
        PrintUtil.printArray(heap);
    }

    public MapHeap(int _capacity) {
        capacity = _capacity;
        heap = new int[capacity];
    }

    public MapHeap() {
        capacity = 11;
        heap = new int[capacity];
    }

    public int peek() {
        return heap[0];
    }

    public int pop() {
        int last = heap[size - 1];
        int peek = peek();
        //移除末尾节点
        heap[size - 1] = 0;
        size--;
        //向下调整
        adjustDown(0, last);
        return peek;
    }

    public boolean push(int e) {
        int i = size;
        if (size >= capacity) {
            System.out.println("超出最大限制");
            return false;
        } else {
            adjustUp(i, e);
            size++;
            return true;
        }
    }


    /**
     * 插入元素到末尾 向上调整
     * 先将元素添加到最末尾，在将其与父节点 (n / 2)比较
     * 小于父节点则交换父节点的值，继续向上调整
     */
    void adjustUp(int i, int e) {
        while (i > 0) {
            //父亲节点在数组中的下标
            int parent = (i - 1) >>> 1;
            int parentE = heap[parent];
            //如果当前节点比父节点大，不需要调整
            if (e >= parentE) {
                break;
            }
            //比父节点小，则与父节点置换，继续向上调整
            heap[i] = parentE;
            i = parent;
        }
        //找到最终位置，赋值
        heap[i] = e;
    }

    /**
     * 移除堆顶元素后，将末尾元素放至顶部，向下调整
     * 如果孩子比它小和它置换
     */
    void adjustDown(int i, int e) {
        //i只需要遍历到(size / 2)即可
        //例如size为8，当i等于4时，其孩子就超出最大范围了
        int half = size >>> 1;
        while (i < half) {
            //左孩子在数组中的下标
            int left = (i << 1) + 1;
            //右孩子在数组中的下标
            int right = left + 1;

            //如果右孩子比左孩子小，和左孩子交换
            if (right < size && heap[right] < heap[left]) {
                left = right;
            }

            //比左右孩子都小，终止
            if (e <= heap[left]) {
                break;
            }

            //继续向下置换，直到调整完成
            heap[i] = heap[left];
            i = left;
        }
        //找到最终位置，赋值
        heap[i] = e;
    }
}
