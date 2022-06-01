package jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * 查看对象数据分布
 *
 * 注意：jdk8开始，默认关闭了偏向锁
 * 需要手动开启偏向锁
 * -XX:+UseBiasedLocking
 */
public class PrintObjectInfo {
    static TestNullObjectSize lock = new TestNullObjectSize();

    public static void main(String[] args) throws InterruptedException {

//        testNoBlock();
//        testBiasLock();
        testThinLock2();
//        testHeavyLock();
        //数组对象会多一个字段,占用四个字节
//        TestNullObjectSize[] test = new TestNullObjectSize[10];
//        System.out.println(ClassLayout.parseInstance(test).toPrintable());
    }

    // 无锁状态下对象头的状态
    // 如果一直没有调用过hashcode方法，对象头里的hashcode对应的25bit都为0
    // 如果调用过hashcode方法，对象头里面的对应字节会变成hashcode
    // 无锁状态下 偏向锁标识为 0 锁标识为 01
    private static void testNoBlock() {
        System.out.println("计算HashCode之前----------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        System.out.println("计算HashCode ：  " + Integer.toHexString(lock.hashCode()));
        System.out.println("计算HashCode之后----------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    // 偏向锁状态下，对象头的状态
    // jvm 参数 -XX:+UseBiasedLocking
    // 第一个调用synchronized的线程，会将lock状态修改为偏向锁状态
    // 并将自己的线程id放进去
    // 我们发现 即使退出synchronized mark word依然是偏向锁的状态 这样是为了方便线程下一次能快速获得锁
    // 偏向锁状态下 偏向锁标识 1 锁标识 01
    private static void testBiasLock() throws InterruptedException {
        System.out.println("加锁之前-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        block();
//        lock.hashCode();
        System.out.println("加锁之后-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    // 测试轻量级锁状态
    // 加入新的线程进行竞争
    // 无锁 -> 偏向锁 -> 轻量级锁 -> 无锁
    // 流程如上所示
    // 这里我们的第一个拿到轻量级锁的线程执行完同步块的任务后，并没有死亡
    // 从上面可以看出
    // 1. 在没有竞争的情况下，第一个拿到锁的线程会将锁升级为偏向锁
    // 2. 在第一个线程执行完后，第二个线程来拿锁时，如果第一个线程还活着，第二个线程会将锁升级为轻量级锁（执行一次CAS操作）
    // 3. 轻量级锁可以退化为无锁，网上的博客说锁无法降级，比较笼统，准确的说是重量级锁无法降级，但是轻量级可以
    // 假如第一个线程死了呢，还会升级为轻量级锁吗，我们试试test2
    private static void testThinLock1() throws InterruptedException {
        System.out.println("加锁之前-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        // 主线程先行，先是偏向锁
        block();
        // 副线程后来，升级为轻量级锁
        new Thread(() -> {
            block();
        }).start();
        Thread.sleep(2000);
        System.out.println("加锁之后-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    // 测试轻量级锁，第一个线程死亡
    // 即使第一个线程死亡了，第二个线程还是会将锁升级为轻量级锁
    // 轻量级锁，可以退化为无锁
    private static void testThinLock2() throws InterruptedException {
        System.out.println("加锁之前-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        // 1线程先行，先是偏向锁
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            block();
        }).start();
        // 2线程后来，升级为轻量级锁
        new Thread(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            block();
        }).start();
        Thread.sleep(1000);
        System.out.println("加锁之后-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    /*
     * 重量级锁状态下，对象头的状态
     *
     * 两个线程同时启动，第一个线程将锁设置为偏向锁
     * 第二个线程此时，不放弃获取锁，采用CAS获取锁，在自选一定次数后
     * 还是没有获取到锁，则升级为重量级别锁，如果获取到了就会变成轻量级锁
     *
     * 我们发现 重量级锁加完以后 lock对象的mark word就不变了
     * 一直是重量级锁的标识
     *
     * 也就是我们说的锁无法降级了
     */
    private static void testHeavyLock() throws InterruptedException {
        System.out.println("加锁之前-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        // 两个线程同时启动，竞争锁，直接为重量级锁
        new Thread(() -> {
            block();
        }).start();
        new Thread(() -> {
            block();
        }).start();
        Thread.sleep(10);
        System.out.println("加锁之后-----------------------------------------------------");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }

    private static void block() {
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + "加锁了-----------------------------------------------------");
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }
}

/**
 一、首先是对象头 12字节
 1. 我们可以看到 mark word是   8个字节
    如果对象被用来作为锁了，对象的mark word是会变化的

    我们以32位JVM为例子
                 mark word 32bit
    ------------------------------------------------------
    hashcode:25      |age: 4| biased_lock:0 | lock_flag:01     无锁状态
    thread_id 23 |epoch 2 |age: 4| biased_lock:1 | lock_flag:01 偏向锁
    ptr_to_lock_record : 30                      | lock_flag:00 轻量级锁
    ptr_to_heavyweight_monitor : 30                | lock_flag:10 重量级锁
    Gc标记：30                                    |lock_flag:11 此时对象会被垃圾收集器回收掉


 2. 第二个字段为 KClass 字段，它告诉Jvm这是哪一个Class的实例， 它是4个字节
    理论上应该是8个字节的，但是Jvm自动开启了压缩功能，所以它是四个字节

    Class就是类加载的时候，加载到元空间的Class
    它是对象实例化的模板

    我们 然后我们发现 我们定义的常量和静态变量并没有占用实例对象的空间
    常量和静态变量，他们是在类加载的时候就加载了，常量属于常量池，静态变量属于 元Class对象
    他们都是单独一份，不属于实例化对象

 二、成员变量 8个字节
 我们定义了两个int类型，占用8个字节

 三、对齐填充 4个字节
 jvm是采用8字节对齐的
 所以后面填充了4个字节，到24字节，这样可以被8整除

 *
 */
/*
 * jvm.TestNullObjectSize object internals:
 * OFF  SZ   TYPE DESCRIPTION               VALUE
 *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
 *   8   4        (object header: class)    0x01001210
 *  12   4    int TestNullObjectSize.a      1
 *  16   4    int TestNullObjectSize.b      1
 *  20   4        (object alignment gap)
 * Instance size: 24 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 */

class TestNullObjectSize {
    public final static int age = 10;

    public final static int number = 10;

    public static int old = 10;

    int a = 1;

    int b = 1;

    private void print() {
        System.out.println("ssss");
    }
}