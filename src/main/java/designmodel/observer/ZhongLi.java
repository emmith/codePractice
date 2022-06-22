package designmodel.observer;

public class ZhongLi extends EventListener {
    public ZhongLi(String _name) {
        name = _name;
    }

    @Override
    public void update(String msg) {
        System.out.println(msg);
        System.out.println(name + "准备看公众号了");
    }
}
