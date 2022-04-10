package dp;

import utils.PrintUtil;

public class PokeBallon {
    public int maxCoins(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        // 构建新数组 1 x x x 1
        int[] val = new int[nums.length + 2];
        System.arraycopy(nums, 0, val, 1, nums.length);
        val[0] = 1;
        val[val.length - 1] = 1;

        // 子问题：dp[i][j] 戳破 （i,j）内气球可以得到的最大金币数（左开右开）
        // 转移方程：dp[i][j] = max(dp[i][k] + val[i] * val[k] * val[j] + dp[k][j])
        //         i < k < j，它是（i,j）内最后一个戳爆的气球
        // 计算顺序：区间 dp

        int[][] dp = new int[val.length][val.length];

        for (int interval = 2; interval < val.length; interval++) {
            for (int i = 0; i + interval < val.length; i++) {

                int j = i + interval;

                int max = 0;
                for (int k = i + 1; k < j; k++) {
                    max = Math.max(max, dp[i][k] + val[i] * val[k] * val[j] + dp[k][j]);
                }

                dp[i][j] = max;
            }
        }

        PrintUtil.printArray(dp);
        return dp[0][val.length - 1];
    }

    public static void main(String[] args) {
        PokeBallon pb = new PokeBallon();
        pb.maxCoins(new int[] {3,1,5,8});
    }
}
