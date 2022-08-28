package designmodel.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * jdk 动态代理需要实现对应接口
 * 而cglib不需要 这里我们直接是使用的AliSmsService，它没有实现接口
 * spring 在aop时，会判断对应的类有没有实现接口
 * 如果实现了会使用jdk动态代理，如果没有实现就会使用cglib动态代理
 *
 * cglib主要借助 enhancer增强类和 我们实现的MethodInterceptor拦截接口
 *
 *
 * jdk 17下
 * 需要添加 VM参数
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * 否则报错
 */
public class CglibProxyFactory {
    public static Object getProxy(Class<?> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new TestMethodInterceptor());
        // 创建代理类
        return enhancer.create();
    }

    public static void main(String[] args) {
        AliSmsService smService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        smService.send("hahaha");
    }
}
