package knapsack;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 背包的最大容量为 W
 * n件物品
 * 每件物品的重量为weight[i],价值为value[i]
 *
 */
public class ZeroOne {
    static int []weight;
    static int []value;
    static int W;
    static int n;
    static int maxValue;

    //用于记忆化搜索
    static int[][] memo;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();//物品数量
        W = sc.nextInt();//背包大小
        weight = new int[n];//物品体积
        value = new int[n];//物品价值

        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
            value[i] = sc.nextInt();
        }

        memo = new int[n + 1][W + 1];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }

//        backTrace(0, 0, 0);
//        System.out.println(maxValue);


        int res = memorizeBackTrace(0, 0);
        System.out.println(res);

    }


    //暴力回溯
    public static void backTrace(int idx, int curWeight, int val) {
        maxValue = Math.max(maxValue, val);
        if (idx == n) {
            return;
        }
        //放
        if (curWeight + weight[idx] <= W) {
            backTrace(idx + 1, curWeight + weight[idx], val + value[idx]);
        }
        //不放
        backTrace(idx + 1, curWeight, val);
    }

    //记忆化搜索
    public static int memorizeBackTrace(int idx, int curWeight) {
        if (idx == n) {
            return 0;
        }

        if (memo[idx][curWeight] != -1) {
            return memo[idx][curWeight];
        }

        //超过容量，不放
        if (curWeight + weight[idx] > W) {
            return memorizeBackTrace(idx + 1, curWeight);
        }
        // 取放和不放中的最大值
        int set = memorizeBackTrace(idx + 1, curWeight + weight[idx]) + value[idx];
        int unset = memorizeBackTrace(idx + 1, curWeight);
        int res = Math.max(set, unset);

        memo[idx][curWeight] = res;
        return res;
    }

    public static int dp2d() {
        //dp[i][j]
        int[][] dp = new int[n + 1][W + 1];
        return 0;
    }
}
