package designmodel.template;

import java.util.stream.IntStream;

/**
 * 模板方法
 * 将公共的字段，逻辑放在模板类
 * 子类选择性的去使用父类的方法，达到自己的需求
 * <p>
 * 比如这里
 * Counter 只是简单的调用了父类的方法
 * SyncCounter 则加了锁再调用父类的方法
 * <p>
 * 如果不用模板方法
 * 两个类都会有 n++ n--的操作
 * 代码重复
 *
 * 模板方法最佳实践 AQS
 */
public class Main {
    public static void main(String[] args) {
        Counter c = new Counter();
        IntStream.range(0, 100).parallel().forEach(i -> {
            for (int j = 0; j < 100; j++) {
                c.inc();
            }
        });
        c.printN();
        SyncCounter s = new SyncCounter();
        IntStream.range(0, 100).parallel().forEach(i -> {
            for (int j = 0; j < 100; j++) {
                s.inc();
            }
        });
        s.printN();
    }
}
