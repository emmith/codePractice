package gc;

/**
 * 测试垃圾回收机制
 */
public class GcLearn {
    private int _10MB = 10 * 1024 * 1024;
    private byte[] memory = new byte[8 * _10MB];

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }


    /**
     * 以虚拟机栈中对象的引用为GC roots
     */
    private static void test1() {
        GcLearn t = method01();
        System.out.println("返回main方法");
        // 此时t指向，此时method01中new出来的GcLearn对象，所以没有回收掉
        System.gc();
        System.out.println("第二次GC完成");
        // 将t设为空，此时method01中new出来的GcLearn没有引用指向它了
        t = null;
        System.gc();
        System.out.println("第三次GC完成");
    }

    public static GcLearn method01() {
        GcLearn t = new GcLearn();
        System.gc();
        // 此时t指向，此时method01中new出来的GcLearn对象，所以没有回收掉
        System.out.println("第一次GC完成");
        return t;
    }

    /**
     * 验证静态变量作为GC roots
     */
    private static void test2() {
        int _10M = 10 * 1024 * 1024;
        GcLearn2 gcLearn2Test = new GcLearn2(4 * _10M);

        gcLearn2Test.gcLearn2 = new GcLearn2(8 * _10M);
        System.gc();
        System.out.println("第一次回收");

        gcLearn2Test = null;
        System.gc();
        System.out.println("第二次回收");
    }

    /**
     * 验证常量作为GC roots
     */
    private static void test3() {
        int _10M = 10 * 1024 * 1024;
        GcLearn3 gcLearn2Test = new GcLearn3(4 * _10M);

        System.gc();
        System.out.println("第一次回收");

        gcLearn2Test = null;
        System.gc();
        System.out.println("第二次回收");
    }

    /**
     * 测试成员变量能否成为GC roots
     */
    private static void test4() {
        int _10M = 10 * 1024 * 1024;
        GcLearn4 gcLearn2Test = new GcLearn4(4 * _10M);
        gcLearn2Test.cc = new GcLearn4(80 * 1024 * 1024);
        System.gc();
        System.out.println("第一次回收");

        gcLearn2Test = null;
        System.gc();
        System.out.println("第二次回收");
    }
}

class GcLearn2 {
    private byte[] memory;

    public static GcLearn2 gcLearn2;

    public static final GcLearn2 GC_LEARN = new GcLearn2(80 * 1024 * 1024);

    public GcLearn2(int capacity) {
        memory = new byte[capacity];
    }
}


class GcLearn3 {
    private byte[] memory;

    public static final GcLearn3 GC_LEARN = new GcLearn3(80 * 1024 * 1024);

    public GcLearn3(int capacity) {
        memory = new byte[capacity];
    }
}

class GcLearn4 {
    private byte[] memory;

    public GcLearn4 cc;

    public GcLearn4(int capacity) {
        memory = new byte[capacity];
    }
}

/**
 * 总结，能作为Gc roots的引用
 * <p>
 * 静态变量引用
 * 常量引用
 * 虚拟机栈中的对象引用
 * <p>
 * 对象的成员变量不能作为Gc roots
 * 他的生命周期随着对象的回收而消失
 */