package bytedance.array;

import java.util.Scanner;

public class SummerSale {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //商品总数
        int n = scanner.nextInt();
        //预算
        int money = scanner.nextInt();
        //白嫖的
        long free = 0;
        //商品信息的数组
        int[][] info = new int[n][4];
        for (int i = 0; i < n; i++) {
            //初始价格
            info[i][0] = scanner.nextInt();
            //折扣后的价格
            info[i][1] = scanner.nextInt();
            info[i][0] -= info[i][1];
            //折扣后的价格减去优惠的部分，小于0表示大于五折，比如4折
            info[i][3] = info[i][1] - info[i][0];
            //快乐值
            info[i][2] = scanner.nextInt();
            //折扣力度大于五折，心理还觉得赚了
            if (info[i][3] < 0) {
                //心里觉得赚了，提高预算
                money -= info[i][3];
                //白嫖的快乐值
                free += (long) info[i][2];
            }
        }
        //dp[i]表示花费i钱时的快乐值
        long[] dp = new long[money + 1];

        for (int i = 0; i < n; i++) {
            if (info[i][3] >= 0) {
                for (int j = money; j >= info[i][3]; j--) {
                    dp[j] = Math.max(dp[j], dp[j - info[i][3]] + (long) info[i][2]);
                }
            }
        }
        System.out.println(dp[money] + free);
    }
}
