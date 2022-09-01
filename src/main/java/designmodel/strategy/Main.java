package designmodel.strategy;


/**
 * 策略模式
 *
 * 通过传入不同的策略得到不同的结果
 * 这里只是计算器的形式展示
 * 如果我们需要添加乘法、除法
 * 只需要添加对应的类，传入不同的策略即可
 *
 * 优点：不用去修改原来的方法了，做到了开放增加，关闭修改
 *
 * 比如我们有个优惠计算的需求
 * 有折扣 满减等不同方式就可以使用策略模式
 *
 * 在创建不同的策略时还可以和工厂模式配合
 * 如何策略可以使用单例 则还可以配合单例模式
 *
 *
 */
public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator(new OperationAdd());
        calc.calc(1, 2);
    }
}
