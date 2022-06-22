package designmodel.observer;

public class Xiao extends EventListener {
    public Xiao(String _name) {
        name = _name;
    }

    @Override
    public void update(String msg) {
        System.out.println(msg);
        System.out.println(name + "准备看小视频了");
    }
}
