package dp;


public class FindLength {
    public int findLength(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int [][]dp = new int[len1][len2];
        int max = 0;

        for (int i = 0;i < len1;i++) {
            if (nums1[i] == nums2[0]) {
                dp[i][0] = 1;
                max = 1;
            }
        }

        for (int i = 0;i < len2;i++) {
            if (nums2[i] == nums1[0]) {
                dp[0][i] = 1;
                max = 1;
            }
        }

        for (int i = 1;i < len1;i++) {
            for (int j = 1;j < len2;j++){
                if (nums1[i] == nums2[j]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

        return max;
    }
}
