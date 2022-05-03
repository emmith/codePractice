package designmodel.factory;

public class OperationMul extends Operation{
    @Override
    public double calc() {
        double res = getX() * getY();
        return res;
    }
}
