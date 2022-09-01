package designmodel.strategy;

public class OperationMinus implements OperationStrategy{

    @Override
    public int calc(int a, int b) {
        return a - b;
    }
}
