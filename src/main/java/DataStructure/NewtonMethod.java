package DataStructure;

/**
 *
 * 牛顿法 求平方根
 * y = x^2
 *
 * f(x) = x^2 - y
 * 等价于求f(x)的零点
 *
 * f(x)' = 2*x
 * 在f(x)上随意选一点 x0, y0，做一条切线，切线交x轴于 x1 点
 * 切线方程为 f(x) - f(x0) = 2*x0(x - x0)
 * 令y等于0
 * x1 = (f(x0) / x0 + x0) / 2
 * x1一定比x更接近零点
 * 只要一直递归的做切线，一定能到达0点
 *
 * 这就是牛顿法，牛顿法也可以用于做梯度下降，非常好用
 *
 *
 */
public class NewtonMethod {
    double bias = 1e-8;
    private double sqrt(double num) {
        double m = num;
        while (Math.abs(num - m * m) > bias) {
            m = (num / m + m) / 2;
        }
        return m;
    }

    //立法根
    private double cube(double num) {
        double m = num;
        while (Math.abs(num - m * m * m) > bias) {
            m = (num / (m*m) + 2 * m) / 3;
        }
        return m;
    }

    public static void main(String[] args) {
        NewtonMethod nm = new NewtonMethod();
        System.out.println(nm.cube(27));
    }
}
