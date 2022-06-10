package designmodel.decorator;

public class Shirt extends Decorator{
    @Override
    public void show() {
        System.out.printf("衬衫 ");
        super.show();
    }
}
