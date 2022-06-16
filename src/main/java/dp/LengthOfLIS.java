package dp;

import java.util.Arrays;

public class LengthOfLIS {
    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int []dp = new int[len];
        Arrays.fill(dp, 1);
        for (int i = 0;i < len;i++) {
            for(int j = 0;j < i;j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i: dp) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
}
