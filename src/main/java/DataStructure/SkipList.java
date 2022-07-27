package DataStructure;

public class SkipList {
    private static final int MAX_LEVEL = 16;
    private static final float SKIP_P = 0.5f;
    private Node head;
    // 用于搜索时保存前置节点
    private Node[] preMemo;

    public SkipList() {
        head = new Node(-1, MAX_LEVEL);
        preMemo = new Node[MAX_LEVEL];
    }

    class Node {
        int val;
        Node[] next;

        public Node(int _val, int level) {
            val = _val;
            next = new Node[level];
        }
    }

    // 随机生成节点层数
    private int randomLevel() {
        int level = 1;
        while (Math.random() < SKIP_P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    //搜索前置节点
    private void findPreNodes(int val) {
        Node cur = head;
        for (int i = MAX_LEVEL - 1; i >= 0; i--) {
            while (cur.next[i] != null && cur.next[i].val < val) cur = cur.next[i];
            preMemo[i] = cur;
        }
    }

    public boolean search(int target) {
        findPreNodes(target);
        return preMemo[0].next[0] != null && preMemo[0].next[0].val == target;
    }

    public void add(int num) {
        findPreNodes(num);
        int level = randomLevel();
        Node node = new Node(num, level);
        for (int i = level - 1; i >= 0; i--) {
            node.next[i] = preMemo[i].next[i];
            preMemo[i].next[i] = node;
        }
    }

    public boolean erase(int num) {
        findPreNodes(num);
        Node node = preMemo[0].next[0];
        if (node == null || node.val != num) return false;
        int level = node.next.length;
        for (int i = level-1; i >= 0;i--) {
            preMemo[i].next[i] = preMemo[i].next[i].next[i];
        }
        return true;
    }

    //打印跳表结构，支持99以下的数字
    private void print() {
        for (int i = MAX_LEVEL - 1;i >= 0;i--) {
            Node cur = head;
            int count = 0;
            while (cur.next[i] != null) {
                int span = cur.next[i].val - cur.val;
                while (span > 1) {
                    System.out.printf("---");
                    span--;
                }
                if (Integer.toString(cur.next[i].val).length() == 2)
                    System.out.printf("%d-", cur.next[i].val);
                else
                    System.out.printf("%d--", cur.next[i].val);
                cur = cur.next[i];
                count++;
            }
            if (count > 0) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        for (int i = 1;i <= 30;i++) {
            skipList.add(i);
        }
        skipList.print();
        System.out.println("查找节点6");
        System.out.println(skipList.search(6));
        skipList.print();
        System.out.println("删除节点3");
        skipList.erase(3);
        skipList.print();

    }
}

