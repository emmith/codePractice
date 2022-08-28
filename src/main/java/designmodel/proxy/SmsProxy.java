package designmodel.proxy;

public class SmsProxy implements SmService {
    SmService smService;

    public SmsProxy(SmService smService) {
        this.smService = smService;
    }

    @Override
    public void send() {
        System.out.printf("before ...\n");
        smService.send();
        System.out.println("after...\n");
    }

    public static void main(String[] args) {
        SmService smService = new SmServiceImpl();
        SmService smProxy = new SmsProxy(smService);
        smProxy.send();
    }
}
