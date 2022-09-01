package designmodel.strategy;

public class Calculator {

    private OperationStrategy strategy;

    public Calculator(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void calc(int a, int b) {
        System.out.println(strategy.calc(a, b));
    }
}
