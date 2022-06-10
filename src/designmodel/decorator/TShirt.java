package designmodel.decorator;

public class TShirt extends Decorator{
    @Override
    public void show() {
        System.out.printf("T恤 ");
        super.show();
    }
}
