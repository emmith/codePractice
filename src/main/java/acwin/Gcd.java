package acwin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;


/**
 * 求最大公约数
 *
 * 三种方法 辗转相除法 更相减损术 优化的更相减损数
 * 如果数不是特别大，还是辗转相除法性能最高
 */
public class Gcd {
    public int calcGreatestCommonDivisor(int a, int b) {
        if (a > b) {
            return gcd1(a, b);
        } else {
            return gcd1(b, a);
        }
    }

    // 辗转相除法，取余运算比较耗时间
    public int gcd1(int a, int b) {
        int val = a % b;
        if (val == 0) {
            return b;
        }
        a = b;
        b = val;
        return gcd1(a, b);
    }

    // 九章算法中的更相减损术，采用减法，但是面对 10000，1这种用例时有点不行，容易OOM
    public int gcd2(int a, int b) {
        if (a == b) {
            return a;
        }
        if (a > b) {
            return gcd2(a - b, b);
        } else {
            return gcd2(b - a, a);
        }
    }

    /**
     * 结合更相减损术和位运算
     * a和b都为偶数 gcd(a,b) = 2 * gcd(a/2, b/2)
     * a为奇数，b为偶数 gcd(a,b) = gcd(a, b/2)
     * a为偶数，b为奇数 gcd(a,b) = gcd(a/2, b)
     * a和b都为奇数，他们的差必为偶数 gcd(a,b) = gcd(b, a-b)
     */
    public int gcd3(int a, int b) {
        if (a == b) {
            return a;
        }
        //保证第一个参数大于第二个
        if (a < b) {
            return gcd3(b, a);
        }
        if (!isOdd(a) && !isOdd(b)) {
            return gcd3(a >> 1, b >> 1) << 1;
        } else if (isOdd(a) && !isOdd(b)) {
            return gcd3(a, b >> 1);
        } else if (!isOdd(a) && isOdd(b)) {
            return gcd3(a >> 1, b);
        }
        return gcd3(b, a - b);
    }

    //判断是否为奇数
    private boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    public static void main(String[] args) {
        Gcd math = new Gcd();
        int a = 1000000000;
        int b = 2000000000;

        StopWatch stopWatch = DateUtil.createStopWatch();
        stopWatch.start("gcd1");
        int res1 = math.calcGreatestCommonDivisor(a, b);
        stopWatch.stop();
        // 任务2
        stopWatch.start("gcd2");
        int res2 = math.gcd2(a, b);
        stopWatch.stop();

        // 任务3
        stopWatch.start("gcd3");
        int res3 = math.gcd3(a, b);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        System.out.printf("res1 : %d\nres2 : %d\nres3 : %d\n", res1, res2, res3);
    }
}
