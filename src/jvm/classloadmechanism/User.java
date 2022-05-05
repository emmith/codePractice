package jvm.classloadmechanism;

public class User {
    private static int a = 10;
    {
        System.out.println("普通代码块");
    }
    static{
        System.out.println("在类加载过程中-----------显示静态代码块----------");
        System.out.println("静态变量a : "+a);
    }
}
