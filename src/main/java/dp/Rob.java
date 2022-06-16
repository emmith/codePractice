package dp;

import java.sql.SQLOutput;
import java.util.Arrays;

public class Rob {
    public int rob(int[] nums) {
        int len = nums.length;
        int []dp = new int[len];
        int max = 0;

        for (int i = 0;i < len;i++) {
            dp[i] = nums[i];
            for(int j = i-2;j >= 0;j--) {
                dp[i] = Math.max(dp[j] + nums[i], dp[i]);
            }
            max = Math.max(dp[i], max);
        }
        System.out.println(Arrays.toString(dp));
        return max;
    }
}
