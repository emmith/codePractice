package designmodel.factory;

public class OperationFactory {
    public static Operation createOperation(Character ch) {
        Operation oper = null;
        switch (ch) {
            case '+':
                oper = new OperationAdd();
                break;
            case '-':
                oper = new OperationSub();
                break;
            case '*':
                oper = new OperationMul();
                break;
            case '/':
                oper = new OperationDiv();
                break;
        }
        return oper;
    }
}
