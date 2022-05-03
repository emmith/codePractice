package designmodel.factory;

public class OperationAdd extends Operation{
    @Override
    public double calc() {
        double res = getX() + getY();
        return res;
    }
}
