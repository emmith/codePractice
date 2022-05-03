package designmodel.singleton;

/**
 * 单例模式
 * 不采用懒加载的方法
 *
 * 懒加载是在调用的时候才创建对象
 * 在多线程下需要加锁，没必要节省这点空间
 */
public class SingleTon1 {
    private final static SingleTon1 INSTANCE = new SingleTon1();

    // 通过静态方法返回实例:
    public static SingleTon1 getInstance() {
        return INSTANCE;
    }

    private SingleTon1() {}
}
