package bytedance.string;

import utils.PrintUtil;

public class Multiply {
    public String multiply(String num1, String num2) {
        if (isZero(num1) || isZero(num2)) {
            return "0";
        }

        int l1 = num1.length();
        int l2 = num2.length();

        int[] res = new int[l1 + l2 + 1];

        for (int i = l2; i > 0; i--) {
            int value1 = num2.charAt(i - 1) - '0';
            for (int j = l1; j > 0; j--) {
                int value2 = num1.charAt(j - 1) - '0';
                res[i + j] += value1 * value2;
            }
        }

        for (int i = res.length - 1; i > 0; i--) {
            int temp = res[i] / 10;
            res[i] = res[i] % 10;
            res[i - 1] += temp;
        }

        //去除前导零
        int i = 0;
        while (i < res.length && res[i] == 0) {
            i++;
        }

        StringBuilder str = new StringBuilder(res.length - i);
        for (int j = i; i < res.length; i++) {
            str.append(res[i]);
        }

        return str.toString();
    }

    public boolean isZero(String str) {
        if (str.length() == 0 || str == null) {
            return false;
        }
        if (str.length() == 1 && str.charAt(0) == '0') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
