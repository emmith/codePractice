package designmodel.proxy.jdkdynamic;

import designmodel.proxy.SmService;
import designmodel.proxy.SmServiceImpl;

import java.lang.reflect.Proxy;

public class JdkProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new TestInvocationHandler(target));
    }

    public static void main(String[] args) {
        SmService smService = (SmService) JdkProxyFactory.getProxy(new SmServiceImpl());
        smService.send("haha Java");
    }
}
