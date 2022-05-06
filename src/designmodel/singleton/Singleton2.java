package designmodel.singleton;

/**
 * 懒汉模式
 */
public class Singleton2 {
    private static Singleton2 INSTANCE = null;

    /**
     * 普通懒汉模式
     *
     * 在多线程下存在问题
     * 如果多个线程同时执行到判断未空哪一行
     * 就会创建多个对象
     * 接下来的赋值语句，也没办法确定是个哪个线程创建的对象
     * 这样会造成空间浪费，GC也需要去回收那些没被使用的对象，浪费GC的时间
     */
    public static Singleton2 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton2();
        }
        return INSTANCE;
    }

    /**
     * 双重锁实现懒汉模式
     *
     * 第一个判空用于防止不必要的加锁
     *
     * 第二个判空同样也是为了防止，别的线程已经加完锁，释放锁，创建完对象了
     * 这样可以保证只有第一次加载的时候，才会初始化对象
     *
     * 但是有一个问题
     * 类的初始化实际上分为三个步骤
     *
     * 1. 开辟内存空间
     * 2. 实例化各个参数
     * 3. 将对象指向内存空间（执行完成这一步，对象就不是空了，因为它指向了内存地址）
     *
     * 但是JVM它有一个指令重排的机制
     * 上面的指令顺序重新排列以后可能会变成1 3 2
     *
     * 这时候我们看
     * 线程1加锁 执行到了new 对象，此时正在初始化
     * 指令重排后为 1 3 2，目前执行完成了3 ，2正在执行
     *
     * 于此同时线程2来了，他判空发现此时对象不为空
     * 直接返回，然后实际上类并没有初始化完成
     *
     * 线程进行一番操作，抛出异常，程序可能崩溃
     * 这就是DCL（double check lock）失效问题
     */
    public static Singleton2 getInstance1() {
        if (INSTANCE == null) {
            synchronized (Singleton2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton2();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 解决双重锁失效问题
     * 需要给对象加上 volatile关键字
     *
     * volatile 可以解决JMM的指令重排序问题
     * 也就是说 指令会按照 1 2 3去执行
     */
    public static Singleton2 getInstance2() {
        if (INSTANCE == null) {
            synchronized (Singleton2.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton2();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 用静态内部类实现 懒汉模式
     *
     * 因为外部类在加载的时候并不会加载内部类
     *
     * 只有当我们第一次调用getInstance3的时候才会初始化INSTANCE
     * 这样做到了懒加载，节省了内存空间
     *
     * 那同时有多个线程同时调用getInstance呢
     *
     * 类在初始化时，会执行一个叫clinit的方法
     * 如果有多个线程同时初始化一个类
     * jvm会保证只有一个线程执行 clinit方法 其他线程会陷入阻塞 直到clinit执行完毕
     * 其他线程被唤醒后将不会再执行clinit方法
     *
     * jvm会保证一个类型只会初始化一次
     *
     * 那这种用静态内部类实现单例模式的方法有什么缺陷呢
     *
     * 个人认为是没有办法传人构造函数的参数
     *
     * 所以如果有传入参数的需要，可以选择DCL方式
     */
    static class SingleTonInner {
        private static Singleton2 INSTANCE = new Singleton2();
    }

    public static Singleton2 getInstance3() {
        return SingleTonInner.INSTANCE;
    }
}
