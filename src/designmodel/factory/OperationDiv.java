package designmodel.factory;

public class OperationDiv extends Operation{
    @Override
    public double calc() {
        double res = getX() / getY();
        return res;
    }
}
