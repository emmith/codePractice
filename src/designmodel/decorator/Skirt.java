package designmodel.decorator;

public class Skirt extends Decorator{
    @Override
    public void show() {
        System.out.printf("裙子 ");
        super.show();
    }
}
