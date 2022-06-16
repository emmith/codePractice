package dp;

import java.util.Arrays;

public class CoinsChange {
    public int coinChange(int[] coins, int amount) {
        //dp[i]表示到i所需的最少硬币数量
        int[] dp = new int[amount + 1];

        for (int coin : coins) {
            if (coin <= amount) {
                dp[coin] = 1;
            }
        }

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin  <= amount - i) {
                    if (dp[i] > 0) {
                        if (dp[coin + i] != 0) {
                            dp[coin + i] = Math.min(dp[coin+i], dp[i] + 1);
                        }else {
                            dp[coin + i] = dp[i] + 1;
                        }
                    }
                }
            }
        }
        return dp[amount];
    }

    public int coinChange2(int[] coins, int amount) {
        //dp[i]表示到i所需的最少硬币数量
        int[] dp = new int[amount + 1];

        for (int i = 0;i < coins.length;i++) {
            for (int j = coins[i];j <= amount;j++) {
                dp[i] += dp[j - coins[i]];
            }
        }
        return dp[amount];
    }

}
