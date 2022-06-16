package acwin;

import utils.PrintUtil;

import java.util.Scanner;

public class ZeroOnePackage {
    static int[] weight;
    static int[] value;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();//物品数量
        int V = sc.nextInt();//背包大小
        weight = new int[N];//物品体积
        value = new int[N];//物品价值

        for (int i = 0; i < N; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
        }

        int[][] memo = new int[N][V + 1];

        PrintUtil.printArray(weight);
        PrintUtil.printArray(value);

//        System.out.println(dfs(0, V, memo));
        System.out.println(dp2d(V));
    }


    public static int dfs(int index, int V, int[][] dp) {
        if (index == weight.length) {
            return 0;
        }
        int res = 0;
        if (dp[index][V] != 0) {
            return dp[index][V];
        }
        if (V < weight[index]) {
            res = dfs(index + 1, V, dp);
        } else {
            res = Math.max(dfs(index + 1, V - weight[index], dp) + value[index], dfs(index + 1, V, dp));
        }
        dp[index][V] = res;
        return res;
    }

    public static int dp1d(int[][] dp) {
        int n = dp.length;//物品数量
        int w = dp[0].length;//容量

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < w; j++) {
                if (j < weight[i]) {
                    dp[i + 1][j] = dp[i][j];
                } else {
                    dp[i + 1][j] = Math.max(dp[i][j], dp[i][j - weight[i]] + value[i]);
                }
            }
        }
        PrintUtil.printArray(dp);
        return dp[n - 1][w - 1];
    }

    public static int dp2d(int V) {
        int[] dp = new int[V + 1];

        for (int i = 0; i < weight.length; i++) {
            for (int j = V; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
            PrintUtil.printArray(dp);
        }
        return dp[V];
    }
}
