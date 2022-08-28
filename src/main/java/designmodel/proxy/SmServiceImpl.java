package designmodel.proxy;

public class SmServiceImpl implements SmService {
    @Override
    public void send() {
        System.out.printf("发送短信\n");
    }
}
