package designmodel.decorator;

public class CasualPants extends Decorator{
    @Override
    public void show() {
        System.out.printf("休闲裤 ");
        super.show();
    }
}
