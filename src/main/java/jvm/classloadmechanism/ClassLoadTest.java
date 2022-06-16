package jvm.classloadmechanism;



public class ClassLoadTest {
    public static void main(String[] args) throws ClassNotFoundException {
        printCurrentClassLoadInfo();
        loadTest();
    }

    /**
     * 打印当前的类加载器的信息
     */
    private static void printCurrentClassLoadInfo() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader);
        System.out.println(loader.getParent());
        System.out.println(loader.getParent().getParent());
        /*
         * jdk.internal.loader.ClassLoaders$AppClassLoader@2aae9190
         * jdk.internal.loader.ClassLoaders$PlatformClassLoader@5e025e70
         * null
         */
    }

    private static void loadTest() {
        try {
            String userClassName = "jvm.classloadmechanism.User";

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            System.out.println(loader);
            System.out.println("Class loader testing");

            System.out.println("------------Class.forName testing-----------");
            Class<?> classForUser = Class.forName(userClassName);
            System.out.println(classForUser.getName());

            System.out.println("------------loader.loadClass testing-----------------");
            Class<?> loaderUser = loader.loadClass(userClassName);
            System.out.println(loaderUser.getName());

            System.out.println("-----------Class.forName appoint loader testing");
            Class<?> classForNameAppointLoader = Class.forName(userClassName, false, loader);
            System.out.println(classForNameAppointLoader.getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*
         *jdk.internal.loader.ClassLoaders$AppClassLoader@2aae9190
         * Class loader testing
         * ------------Class.forName testing-----------
         * 在类加载过程中-----------显示静态代码块----------
         * 静态变量a : 10
         * jvm.classloadmechanism.User
         * ------------loader.loadClass testing-----------------
         * jvm.classloadmechanism.User
         * -----------Class.forName appoint loader testing
         * jvm.classloadmechanism.User
         *
         * 1.
         * Class.forName 这种用反射加载类的机制 默认会初始化静态代码
         * 也可以指定类加载器并选择不初始化的方式
         * forName(name, false, loader)
         * 指定某个加载器来加载，
         *
         * 看源代码我们发现它是调用了一个native方法 forName0
         * jvm调用这个forName0方法，后面又会回调到ClassLoader来进行类加载
         *
         * 2.
         * loader.loadClass 则默认不会初始化静态代码
         * loadClass还有一个重载方法，含有两个参数
         * 第一个参数是ClassName,第二个参数决定是否初始化
         * 这个重载方法是protected修饰的，就是说只有同一个包下或者子类才能调用
         * 这个重载方法采用了synchronize修饰，它会锁住对应的class,防止一个class在多个线程同时加载
         * 保证每个class只会存在一个对应的class实例
         *
         * 我们调用的loadClass方法就是默认第二个参数为false也就是不初始化
         * 假如我们想将其修改为true,我们可以继承classloader,自定义
         *
         *
         * 3. 所以类加载最后实际上还是调用ClassLoader来加载
         * 我们看ClassLoader的源码
         *
         * 首先 调用一个方法判断有没有加载过
         * 如果加载过，初始化其静态代码后直接返回
         *
         * 如果没有加载过
         * 就先问问父亲有没有加载 applicationClassLoader 问 PlatFormClassLoader
         * 加载过，则返回
         * 没有，那么PlatformClassLoader 再问 bootstrapClassLoader
         * 加载过，则返回
         * 如果bootStrapClassLoader也没有加载过
         * 它就会尝试加载，如果加载成功，就返回
         *
         * 没有加载成功交给孩子PlatFormClassLoader来加载
         * 孩子加载成功了，返回
         *
         * 没有成功，就交给最下面的applicationClassLoader来加载
         * 加载成功，则返回，没有加载成功
         *
         * 就会抛出一个异常ClassNotFoundException
         *
         * 这种机制叫做双亲委派机制
         *
         * 那么为什么要这样加载呢？
         * 1. 这样可以保证一个类只会被加载一次，避免重复加载
         * 2. 不同的加载器加载不同的类，bootstrap加载java核心类，一般以java.开头
         * platform加载扩展类，一般以javax开头，application加载用户类，这样隔离可以
         * 防止核心类被篡改
         *
         *
         * 不过发现，最顶层的bootstrapClassLoader好像没办法调用底层加载的类
         *
         */
    }
}
