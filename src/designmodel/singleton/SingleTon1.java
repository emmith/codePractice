package designmodel.singleton;

/**
 * 单例模式
 *
 * 饿汉模式
 * 在类被初始化的时候就会在内存中创建对象
 *
 * 类初始化的顺序是
 * 静态代码块、变量、方法
 * 实例变量、方法
 * 然后才是构造方法
 *
 * 如果有父亲
 * 则是
 * 父亲静态代码块、变量、方法
 * 子类静态代码块、变量、方法
 * 父亲实例变量、方法
 * 父亲构造方法
 * 子类实例变量、方法
 * 子类构造方法
 *
 * 也就是说类在第一次加载的时候就会初始化静态代码，类的所以实例都会共享他们
 * 假如这些静态代码我们可能一直没有使用
 * 就会造成空间浪费
 *
 * 静态代码只有在类被销毁时才会消失
 * 所以为了节省空间
 *
 * 有了懒汉模式
 */
public class SingleTon1 {
    private final static SingleTon1 INSTANCE = new SingleTon1();

    // 通过静态方法返回实例:
    public static SingleTon1 getInstance() {
        return INSTANCE;
    }

    private SingleTon1() {}
}
