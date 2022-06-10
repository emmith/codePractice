package designmodel.decorator;

public class Jeans extends Decorator{
    @Override
    public void show() {
        System.out.printf("牛仔裤 ");
        super.show();
    }
}
