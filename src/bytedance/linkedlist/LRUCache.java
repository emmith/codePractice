package bytedance.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    class DList {
        int key;
        int value;
        DList prev;
        DList next;
        DList() {}
        DList(int _key, int _value) {
            key = _key;
            value = _value;
        }
    }
    DList head;
    DList tail;
    Map<Integer,DList> memo;
    int size;
    int capacity;
    public LRUCache(int _capacity) {
        size = 0;
        memo = new HashMap<>(_capacity);
        capacity = _capacity;
        head = new DList();
        tail = new DList();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int _key) {
        DList node = memo.get(_key);
        if (node != null) {
            moveToHead(node);
            System.out.println(node.value);
            return node.value;
        }
        System.out.println(-1);
        return -1;
    }

    public void put(int _key, int _value) {
        //先判断缓存中有没有需要存入的key
        if (memo.containsKey(_key)) {
            //有，则更新缓存，移动到队列头部
            DList node = memo.get(_key);
            node.value = _value;
            moveToHead(node);
        }else {
            //没有，需先判断队列的大小
            if (size < capacity) {
                //没有存满，直接存入缓存，添加到头部
                pushNewToCache(_key, _value);
            }else {
                //满了，需要先移除末尾元素再添加元素
                DList nodeToRemove = removeTail();
                System.out.println("移除末尾元素" + nodeToRemove.key);
                memo.remove(nodeToRemove.key);
                pushNewToCache(_key, _value);
            }
        }
    }

    //存入一个新的节点
    private void pushNewToCache(int _key, int _value) {
        DList node = new DList(_key, _value);
        addToHead(node);
        memo.put(_key, node);
    }

    //添加新节点到头部
    private void addToHead(DList node) {
        head.next.prev = node;
        node.prev = head;
        node.next = head.next;
        head.next = node;
        size++;
    }

    //删除节点
    private void removeNode(DList node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        size--;
    }

    //将节点移动到头部
    private void moveToHead(DList node) {
        removeNode(node);
        addToHead(node);
    }


    //删除尾部节点
    private DList removeTail() {
        DList node = tail.prev;
        removeNode(node);
        return node;
    }

}
