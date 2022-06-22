package designmodel.observer;

public class ZhongLi implements EventListener{
    @Override
    public void update(String msg) {
        System.out.println(msg);
        System.out.println("Zhongli 准备看公众号了");
    }
}
