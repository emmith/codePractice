package thread.synchronize;

public class TicketSynchronied {

    public static void main(String[] args) throws InterruptedException {
        testTicketConsumer2();
    }

    private static void testTicketConsumer1() throws InterruptedException {
        TicketConsumer1 consumer1 = new TicketConsumer1(10);
        TicketConsumer1 consumer2 = new TicketConsumer1(10);
        Thread why = new Thread(consumer1, "why");
        Thread mx = new Thread(consumer2, "mx");
        why.start();
        mx.start();
        why.join();
        mx.join();
        System.out.println("当前剩余票数: " + consumer1.getTicket());
    }

    private static void testTicketConsumer2() throws InterruptedException {
        TicketConsumer2 consumer1 = new TicketConsumer2(10);
        TicketConsumer2 consumer2 = new TicketConsumer2();
        Thread why = new Thread(consumer1, "why");
        Thread mx = new Thread(consumer2, "mx");
        why.start();
        mx.start();
        why.join();
        mx.join();
        System.out.println("当前剩余票数: " + consumer1.getTicket());
    }
}

/**
 * 这里synchronized锁住的对象为 Object类型
 */
class TicketConsumer1 implements Runnable {

    private final static Object lock = new Object();

    private volatile static int ticket;

    public TicketConsumer1(int ticket) {
        this.ticket = ticket;
    }

    public int getTicket() {
        return ticket;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "开始抢第" + ticket + "张票，对象加锁之前：" + System.identityHashCode(lock));
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "抢到第" + ticket + "张票，成功锁到的对象：" + System.identityHashCode(lock));
                if (ticket > 0) {
                    try {
                        //模拟抢票延迟
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到了第" + ticket-- + "张票，票数减一");
                } else {
                    return;
                }
            }
        }
    }
}

/**
 * 这里synchronized锁住的对象为 Ticket, Integer类型
 */
class TicketConsumer2 implements Runnable {

    private volatile static Integer ticket;

    public TicketConsumer2(int ticket) {
        this.ticket = ticket;
    }

    public int getTicket() {
        return ticket;
    }

    public TicketConsumer2() {

    }

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "开始抢第" + ticket + "张票，对象加锁之前：" + System.identityHashCode(ticket));
            synchronized (ticket) {
                System.out.println(Thread.currentThread().getName() + "抢到第" + ticket + "张票，成功锁到的对象：" + System.identityHashCode(ticket));
                if (ticket > 0) {
                    try {
                        //模拟抢票延迟
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到了第" + ticket-- + "张票，票数减一\n");
                } else {
                    return;
                }
            }
        }
    }
}

/**
 * why开始抢第10张票，对象加锁之前：1405370640
 * mx开始抢第10张票，对象加锁之前：1405370640
 * why抢到第10张票，成功锁到的对象：1405370640
 * why抢到了第10张票，票数减一
 * why开始抢第9张票，对象加锁之前：28585601
 *
 *
 * mx抢到第9张票，成功锁到的对象：28585601
 * why抢到第9张票，成功锁到的对象：28585601
 *
 * 这里为什么会出现两个线程同时抢到一把锁？
 *
 * 我们先要知道上锁到底干了什么
 *
 * 举个例子，我们有一个会议室，会议室有扇门，这扇门就是锁
 * 如果会议室里面有人，这扇门没办法打开
 * 只有会议室没有人的时候，我们可以推开这扇门
 *
 * 上锁的本质就是限制同一时间人的访问
 *
 * 对于java中的上锁，也就是锁里面的代码
 * 同一时刻只有一个线程能访问
 * 而代码在内存中
 * 进一步，我们可以认为
 * 同一时刻，某一段内存区域只有一个线程可以访问
 *
 * 申请锁，本质上是申请内存中地址的访问权限
 *
 * System.identityHashCode 这个是获得对象的hashcode
 * java中是使用hashcode来区分对象的
 *
 * 如果一个对象持有了锁，jvm会把这把锁的hashcode放入这个申请者的头部
 * 这样我们就可以知道一个对象申请了多少把锁
 *
 * 那为什么会出现两个线程同时申请到锁的情况呢
 * 我们不能怀疑jdk有问题，synchronized中的代码
 * 如果是同一把锁，肯定同时只能有一个线程进入
 * 那我们用理论推理
 * 两个线程肯定是申请的不是同一把锁
 *
 * 那怎么证明呢，用jstack
 * 我们用jstack抓到如下
 *
 * 这是第一次抢票的时候
我们看到 两个线程申请锁的地址是一样的<0x00000007ffe7c708>
mx线程先申请到锁 why线程在blocked状态

"why" #15 prio=5 os_prio=0 cpu=13.33ms elapsed=0.61s tid=0x00007efd5825dbd0 nid=0x5b7be waiting for monitor entry  [0x00007efd1edfc000]
   java.lang.Thread.State: BLOCKED (on object monitor)
	at thread.synchronize.TicketConsumer2.run(TicketSynchronied.java:97)
	- waiting to lock <0x00000007ffe7c708> (a java.lang.Integer)
	at java.lang.Thread.run(java.base@17.0.3/Thread.java:833)


"mx" #16 prio=5 os_prio=0 cpu=11.87ms elapsed=0.61s tid=0x00007efd5825e9b0 nid=0x5b7bf waiting on condition  [0x00007efd1ecfc000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
	at java.lang.Thread.sleep(java.base@17.0.3/Native Method)
	at thread.synchronize.TicketConsumer2.run(TicketSynchronied.java:101)
	- locked <0x00000007ffe7c708> (a java.lang.Integer)
	at java.lang.Thread.run(java.base@17.0.3/Thread.java:833)


这是第二次抢票
why线程锁的位置是<0x00000007ffe7c708>
mx线程锁的位置是<0x00000007ffe7c6f8>
我们看到两个线程锁的地址都变了
也就是说两个线程申请的锁不一样
所以他们能同时进入synchronized锁住的代码块

"why" #15 prio=5 os_prio=0 cpu=13.50ms elapsed=10.09s tid=0x00007efd5825dbd0 nid=0x5b7be waiting on condition  [0x00007efd1edfc000]
    java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.3/Native Method)
    at thread.synchronize.TicketConsumer2.run(TicketSynchronied.java:101)
    - locked <0x00000007ffe7c708> (a java.lang.Integer)
    at java.lang.Thread.run(java.base@17.0.3/Thread.java:833)


"mx" #16 prio=5 os_prio=0 cpu=15.28ms elapsed=10.09s tid=0x00007efd5825e9b0 nid=0x5b7bf waiting on condition  [0x00007efd1ecfc000]
    java.lang.Thread.State: TIMED_WAITING (sleeping)
    at java.lang.Thread.sleep(java.base@17.0.3/Native Method)
    at thread.synchronize.TicketConsumer2.run(TicketSynchronized.java:101)
    - locked <0x00000007ffe7c6f8> (a java.lang.Integer)
    at java.lang.Thread.run(java.base@17.0.3/Thread.java:833)

 那为什么锁的hashcode和地址一直在变？
 因为Integer是一个对象，他在执行加减操作的时候实际上会创建新的对象
 为了减少创建销毁对象的资源消耗
 Integer中设置了对象池，存储了[-128,127]的Integer对象
 如果我们new这个范围内的Integer对象，会直接返回对应的对象

 我们执行Integer--的时候
 会返回一个新的对象
 所以地址和hashcode会一直变

 那hashcode和地址是什么关系，它是不是地址 ？
 hashcode ： 1405370640 对应的16进制为 0x0000000053c44110
 而地址是 <0x00000007ffe7c708>
 不一样
 关于hashcode,在hashcode这个native方法中有五种选项
 1 随机
 2 固定为1
 3 从0开始递增
 4 使用内存地址
 5 用状态信息等进行异或运算得出  （默认为这个）

 所以如果我们想让hashcode和内存地址一样
 可以将jvm中hashcode的参数设置为4
 有一家公司发布的jdk就将这个参数默认设置为了4

 那为什么两个线程锁的hashcode一样？
 同一个对象的hashcode当前一样啊

 hashcode一样为什么锁不一样
 1. 申请锁，实际上是对内存进行锁定，线程1是锁定了10这个数指向地址
    此时线程2,还在申请指向10这个地址的锁
 2. 线程1释放了10指向的这个地址后，线程2用这一块地址为锁，进入临界区
    此时ticket对象已经变成了9,其指向的物理地址也变了，线程1用这个物理地址申请到锁，也进入临界区
 3. 此时线程2去访问ticket对象，它已经变成了9,我们得到了Integer对象池中9的hashcode，然后线程2睡了
    然后线程1也去访问ticket对象，它还是9,所以我们得到了和线程2相同的hashcode，然后线程1也睡了
 */