package designmodel.strategy;

public class OperationAdd implements OperationStrategy{
    @Override
    public int calc(int a, int b) {
        return a + b;
    }
}
