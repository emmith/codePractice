package jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    private static Unsafe unsafe = getUnsafeCorrectDemo();

    /**
     * 错误示范！！！
     *
     * 获取Unsafe类
     * 我们看源代码可以发现这样一行代码 !VM.isSystemDomainLoader(caller.getClassLoader())
     * 再去查看isSystemDomainLoader方法
     * return loader == null || loader == ClassLoader.getPlatformClassLoader();
     * 只有当前的类加载器为bootstrap 或者 platform时才可以调用这个方法
     * 否则会抛出异常
     *
     * Exception in thread "main" java.lang.SecurityException: Unsafe
     * 	at jdk.unsupported/sun.misc.Unsafe.getUnsafe(Unsafe.java:99)
     * 	at jvm.UnsafeTest.getUnsafe(UnsafeTest.java:11)
     * 	at jvm.UnsafeTest.main(UnsafeTest.java:17)
     *
     * 所以如果我们想要调用这个静态方法加载Unsafe类
     * 需要用bootstrapClassLoader 或者 platformClassLoader来加载
     * 这个需要调用Unsafe的类
     * 可以将对应对应的jar包放入对应的classloader的目录下
     *
     */
    private Unsafe getUnsafeErrorDemo() {
        return Unsafe.getUnsafe();
    }

    /**
     * 正确示范
     * 使用反射获得对象
     */
    private static Unsafe getUnsafeCorrectDemo() {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        try {
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            return unsafe;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  unsafe 操作内存的测试
     *
     *  这里我们只测试了int类型
     *  也可以放对象的
     *  调用类似于putObject的方法
     *
     *  如果我们需要访问对象中的熟悉
     *  需要 对象起始地址加偏移量访问的方式
     *
     *  那什么时候使用堆外的内存呢
     *  堆外内存是由操作系统来管理的，使用堆外内存，减少堆内内存的大小，可以降低GC时STW对应用的影响
     *  对于那些需要从堆内拷贝到堆外而且存活时间有不长的对象，我们尽量分配到堆外内存，减少IO的次数
     */
    private void memoryTest() {
        // 分配一个整数的大小的内存，这是没有初始化的内存，操作系统会将其视为垃圾
        // 初始化内存可以调用unsafe.setMemory(),这个方法会将对应的byte设置为0
        // unsafe.setMemory(base, 4L, (byte) 0);
        // 例如这里我们申请了4个字节，初始化的话就会得到4个0
        long base = unsafe.allocateMemory(4);

        String addr = Long.toHexString(base);
        System.out.println("分配到的整数的内存地址为 : " + addr);

        // 用putInt赋值
        unsafe.putInt(base, 5);
        System.out.printf("调用putInt对内存地址 %s 赋值， 赋值为 %d\n", addr, 5);

        // 用getInt取得对应内存的值
        int val = unsafe.getInt(base);
        System.out.printf("调用putInt对内存地址 %s 取值， 取值为 %d\n", addr, val);

        // 释放内存
        unsafe.freeMemory(base);

    }

    /**
     *  CAS compare and swap
     *  假如我有一个整数变量 10 现在我想把他修改为 11
     *  一般情况是 我们直接将11写到内存，但是这在多线程环境下是不安全的，因为可能存在其他线程在对内存进行操作
     *
     *  cas是，写入之前先判断内存中的值是不是我们修改之前的值，是就直接写入，不是则写入失败
     *  这里就是 先判断内存中是不是10,是代表没有别的线程修改，写入11,不是10了，表示别的线程修改了，返回修改失败
     *
     *  这里存在一个问题：比如有别的线程将10修改成9,之后又有线程将其修改为10,当前线程去看内存的时候是10,实际上内存早就被修改过了
     *  这就是ABA问题，针对这个问题，我们得再加一个时间戳，时间戳相同而且值相同，我们才能修改成功
     *
     *  如果要修改多个值呢
     *  如果是整数类型，可以考虑将多个数放在一个数的不同bit位上，比如前几位放状态位，后几位放别的
     *  这种应用在JDK中十分广泛，例如线程池就是将线程池的状态和工作线程数量放在一个Int类型中
     *
     *  如果要修改很多类型
     *  可以调用
     *  unsafe.compareAndSwapObject()
     *  这个可以修改对象
     *
     *  实际上JDK已经给我们封装了很多类型比如
     *  AtomicInteger
     *  AtomicReference 等等
     *  就是基于unsafe中的Cas实现的
     *
     */
    private void casTest() {
        Integer a = Integer.valueOf(200);
        System.out.println("CAS之前的值 " + a);
        try {
            // 获取对象字段的偏移量
            long offset = unsafe.objectFieldOffset(Integer.class.getDeclaredField("value"));
            // 调用unsafe类中的cas机制
            unsafe.compareAndSwapInt(a, offset, 200, 201);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CAS之后的值 " + a);
    }

    /**
     * unsafe 还可以挂起线程
     */
    private void threadTest() {
        long time = System.currentTimeMillis();
        // 第一个参数为true表示按绝对时间挂起，所以这里我先取系统当前时间，加1000表示挂起一秒
        // 当第一个参数为true的时候，时间的单位为毫米
        unsafe.park(true, time + 1000);
        System.out.println("hhh");

        // 当第一个参数为false的时候，时间的单位为纳秒
        unsafe.park(false, 3000000000L);
        System.out.println("jjjj");
    }


    public static void main(String[] args) {
        UnsafeTest unsafeTest = new UnsafeTest();
        unsafeTest.threadTest();
    }
}


