package designmodel.factory;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double x = sc.nextDouble();
        Character oper = sc.next().charAt(0);
        double y = sc.nextDouble();

        Operation operation = OperationFactory.createOperation(oper);
        operation.setX(x);
        operation.setY(y);
        System.out.println(operation.calc());

    }
}
