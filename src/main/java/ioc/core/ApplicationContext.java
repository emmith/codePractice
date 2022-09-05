package ioc.core;

import cn.hutool.core.util.StrUtil;
import ioc.annotation.Autowired;
import ioc.annotation.Component;
import ioc.annotation.ComponentScan;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private final Map<String, Class<?>> classMap = new ConcurrentHashMap<>(16);

    // 单例成品bean，属性都填充完成的bean
    // spring中有三级缓存，这里只是模拟了两级缓存
    // 少了一个Factory的缓存，在spring中factory是用来做类增强的，如果我们增强这一层缓存可以不要
    // 两级缓存必不可少
    // 可以使用反证法证明
    // 如果只有一级缓存，类之间存在循环依赖
    // 1. 如果在类完全创建完成后，才将类放入map中，一定会无限递归
    // 2. 如果在类中实例化后，马上放入map中，虽然解决了循环依赖，但是存在类属性缺少问题
    // 比如 A 有两个属性 B C，B只有属性A
    // 加载A--->A放入map中---->加载A的属性B--->把B放入B中--->B的属性A存在于map中--->B实例化完成
    // 但实际上A并没有实例化完成，A还有一个属性C没有实例化
    private final Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>(16);

    // 半成品单例bean，属性还没填充完成的bean
    private final Map<String, Object> earlyBeanMap = new ConcurrentHashMap<>(16);

    private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public ApplicationContext(Class<?> configClass) {
        // 1.扫描配置信息中指定包下的类
        this.scan(configClass);
        // 2.实例化扫描到的类
        this.instantiateBean();
    }

    private void scan(Class<?> configClass) {
        // 解析配置类，获取到扫描包路径
        String basePackages = this.getBasePackages(configClass);
        System.out.println("获取到包路径..." + basePackages);
        // 使用扫描包路径进行文件遍历操作
        this.doScan(basePackages);
    }

    private String getBasePackages(Class<?> configClass) {
        // 从ComponentScan注解中获取扫描包路径
        ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
        return componentScan.basePackages();
    }

    private void doScan(String basePackages) {
        // 获取资源信息
        URL resource = getResource(basePackages);

        File dir = new File(resource.getPath());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                // 递归扫描
                doScan(basePackages + "." + file.getName());
            } else {
                // com.my.spring.example + . + Boy.class -> com.my.spring.example.Boy
                String className = basePackages + "." + file.getName().replace(".class", "");
                // 将class存放到classMap中
                this.registerClass(className);
            }
        }
    }

    private URL getResource(String packagePath) {
        return classLoader.getResource(packagePath.replace('.', '/'));
    }

    private void registerClass(String className) {
        try {
            // 加载类信息
            Class<?> clazz = classLoader.loadClass(className);
            // 判断是否标识Component注解
            if (clazz.isAnnotationPresent(Component.class)) {
                // 生成beanName com.my.spring.example.Boy -> boy
                String beanName = generateBeanName(clazz);
                // car: com.my.spring.example.Car
                classMap.put(beanName, clazz);
                System.out.printf("注册到类信息 %s %s\n", beanName, clazz.getName());
            }
        } catch (ClassNotFoundException ignore) {
        }
    }

    private String generateBeanName(Class<?> clazz) {
        String name = clazz.getSimpleName();
        return StrUtil.toCamelCase(name);
    }

    public void instantiateBean() {
        for (String beanName : classMap.keySet()) {
            getBean(beanName);
        }
    }

    public Object getBean(String beanName) {
        System.out.println("准备实例化" + beanName);
        return getSingleton(beanName);
    }

    private Object getSingleton(String beanName) {
        // 优先从成品bean中获取
        Object bean = singletonBeanMap.get(beanName);
        if (bean == null) {
            System.out.println(beanName + "不在成品单例池中");
            // 尝试从半成品池中获取
            bean = earlyBeanMap.get(beanName);
            if (bean == null) {
                System.out.println(beanName + "不在半成品池中，准备创建bean");
                bean = createBean(beanName);
            }
        }
        return bean;
    }

    private Object createBean(String beanName) {
        Class<?> clazz = classMap.get(beanName);
        try {
            // 创建bean
            Object bean = this.doCreateBean(clazz);
            earlyBeanMap.remove(beanName);
            singletonBeanMap.put(beanName, bean);
            System.out.println("将完全体对象" + beanName + "放入缓存中........................");
            return bean;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object doCreateBean(Class<?> clazz) throws IllegalAccessException {
        // 实例化bean
        Object bean = this.newInstance(clazz);
        System.out.println("实例化" + bean.getClass().getSimpleName() + "半成品完成");
        // 将bean存到半成品容器中，此时bean的属性还没初始化完成
        earlyBeanMap.put(clazz.getSimpleName(), bean);
        // 填充字段，将字段设值
        this.populateBean(bean, clazz);
        return bean;
    }

    private Object newInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateBean(Object bean, Class<?> clazz) throws IllegalAccessException {
        System.out.println("---------准备实例化对象" + bean.getClass().getSimpleName() + "的字段-----------");
        // 解析class信息，判断类中是否有需要进行依赖注入的字段
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {      // 获取bean
                Object value = this.resolveBean(field.getType(), bean);
                field.setAccessible(true);
                field.set(bean, value);
            }
        }
        System.out.println("----------字段实施化完成-----------");
    }

    private Object resolveBean(Class<?> clazz, Object bean) {
        // 先判断clazz是否为一个接口，是则判断classMap中是否存在子类
        if (clazz.isInterface()) {
            // 暂时只支持classMap只有一个子类的情况
            for (Map.Entry<String, Class<?>> entry : classMap.entrySet()) {
                if (clazz.isAssignableFrom(entry.getValue())) {
                    return getBean(entry.getValue());
                }
            }
            throw new RuntimeException("找不到可以进行依赖注入的bean");
        } else {
            return getBean(clazz);
        }
    }

    public Object getBean(Class<?> clazz) {
        // 生成bean的名称
        String beanName = generateBeanName(clazz);
        // 此处对应最开始的getBean方法
        return getBean(beanName);
    }
}