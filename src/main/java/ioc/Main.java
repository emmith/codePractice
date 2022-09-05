package ioc;

import ioc.annotation.ComponentScan;
import ioc.core.ApplicationContext;
import ioc.example.Boy;
import ioc.example.test.A;

import java.net.URISyntaxException;

@ComponentScan(basePackages = "ioc.example")
public class Main {
    public static void main(String[] args) throws URISyntaxException {
        test2();
    }

    // 测试注入功能
    private static void test1() {
        ApplicationContext context = new ApplicationContext(Main.class);
        Boy boy = (Boy) context.getBean("Boy");
        boy.drive();
    }

    // 测试循环引用
    // 理论上业务中中不应该存在循环引用，如果存在代码设计一定有问题
    private static void test2() {
        ApplicationContext context = new ApplicationContext(Main.class);
        A a = (A)context.getBean("A");
        a.getB().say();
        a.getB().getC().say();
        a.getC().getA().say();
        a.getC().getA().getC().getB().getC().getB().say();
    }
}
