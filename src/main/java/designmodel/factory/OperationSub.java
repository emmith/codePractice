package designmodel.factory;

public class OperationSub extends Operation{
    @Override
    public double calc() {
        double res = getX() - getY();
        return res;
    }
}
