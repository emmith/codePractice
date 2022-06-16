package dfs;

import java.util.ArrayList;
import java.util.List;

public class RestoreIpAddresses {
    //用于存放ip地址的四个段
    int[] segments = new int[4];

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() > 12) {
            return res;
        }
        dfs(s, 0, res, 0, 0, 0);
//        res.stream().forEach(System.out::println);
        return res;
    }

    //countDot 用于记录小数点个数, count 记录当前段的位数
    public void dfs(String s, int index, List<String> res, int val, int countDot, int count) {
        //遍历完成，且小数点个数为三
        if (index == s.length() && countDot == 3) {
            res.add(seg2Ip());
            return;
        }

        //剪支，小数点大于三直接return
        if (index == s.length() || countDot > 3) {
            return;
        }

        int cur = s.charAt(index) - '0';
        int temp = val * 10 + cur;

        //有前导零，例如01这样的段，计算小于10，直接return
        if (count == 1 && temp < 10) {
            return;
        }

        if (temp <= 255) {
            //将当前段存于数组中，以小数点为下标
            segments[countDot] = temp;
            //小数点加一
            if (index + 1 < s.length()) {//防止在末尾加小数点
                dfs(s, index + 1, res, 0, countDot + 1, 0);
            }
            //不加小数点
            dfs(s, index + 1, res, temp, countDot, count + 1);
        }
    }

    //将段拼接为ip地址
    public String seg2Ip() {
        StringBuilder ip = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            ip.append(segments[i]);
            if (i < 3)
                ip.append('.');
        }
        return ip.toString();
    }

    public static void main(String[] args) {
        RestoreIpAddresses restoreIpAddresses = new RestoreIpAddresses();
        restoreIpAddresses.restoreIpAddresses("0000");
    }
}
