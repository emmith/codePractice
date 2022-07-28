package acwin;

import java.util.Arrays;

public class FractionAddition {

    public static void main(String[] args) {
        FractionAddition fc = new FractionAddition();
        String input = "+9/9+8/8+3/3+2/1";
        System.out.println(fc.fractionAddition(input));
    }

    public String fractionAddition(String expression) {
        int l = expression.length() / 4 + 1;
        int[] denominator = new int[l];
        int[] molecule = new int[l];
        int[] set = new int[11];
        int idx = 0;

        if (expression.charAt(0) >= '0' && expression.charAt(0) <= '9') {
            expression = "+" + expression;
        }

        for (int i = 0; i < expression.length(); ) {
            int cur = 0;
            int flag = 0;
            // 1. 获取分子符号
            if (expression.charAt(i) == '-') {
                flag = 1;
            }
            i++;
            // 2. 获取分子
            while (expression.charAt(i) != '/') {
                cur = cur * 10 + (expression.charAt(i) - '0');
                i++;
            }
            if (flag == 1) {
                cur = -cur;
            }
            molecule[idx] = cur;
            i++;
            cur = 0;
            // 3. 获取分母
            while (i < expression.length() && expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                cur = cur * 10 + (expression.charAt(i) - '0');
                i++;
            }
            denominator[idx++] = cur;
            set[cur]++;
        }
        System.out.println(Arrays.toString(molecule));
        System.out.println(Arrays.toString(denominator));
        // 4. 求分母的最大公约数
        int k = 1;
        for (int i = 1; i < 11; i++) {
            if (set[i] > 0) {
                k *= i;
            }
        }
        System.out.println("k : " + k);
        int ans = 0;

        for (int i = 0; i < idx; i++) {
            int ratio = k / denominator[i];
            ans += ratio * molecule[i];
        }
        System.out.println("ans : " + ans);
        if (ans == 0) {
            return "0/1";
        }
        boolean flag = true;
        if (ans < 0) {
            ans = -ans;
            flag = false;
        }
        int greatestCommonDivisor = gcd(ans, k);

        System.out.println("greatestCommonDivisor : " + greatestCommonDivisor);

        k /= greatestCommonDivisor;
        ans /= greatestCommonDivisor;

        String str = ans + "/" + k;
        if (flag == false) {
            str = "-" + str;
        }

        return str;
    }

    private int gcd(int a, int b) {
        if (a < b) {
            return gcd(b, a);
        }
        int c = a % b;
        if (c == 0) {
            return b;
        }
        a = b;
        b = c;
        return gcd(a, b);
    }
}
