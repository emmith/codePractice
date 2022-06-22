package designmodel.observer;

public class Xiao implements EventListener {
    @Override
    public void update(String msg) {
        System.out.println(msg);
        System.out.println("Xiao 准备看小视频了");
    }
}
