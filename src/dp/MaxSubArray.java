package dp;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 子数组 是数组中的一个连续部分。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 * 示例 2：
 *
 * 输入：nums = [1]
 * 输出：1
 * 示例 3：
 *
 * 输入：nums = [5,4,-1,7,8]
 * 输出：23
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 *  
 *
 * 进阶：如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的 分治法 求解。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MaxSubArray {
    public int maxSubArray(int[] nums) {
        int len = nums.length;
        /**
         * dp[i] 表示以i为结尾的最大连续子数组的大小
         * 如果nums[i+1]大于零 dp[i+1] = dp[i] + nums[i+1]
         * 如果nums[i+1]小于零 dp[i+1] = Math.max(dp[i]+num[i+1], nums[i+1])
         */
        int []dp = new int[len];
        //初始化，当i为零的时候，dp[i]等于nums[0]
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 0;i < len - 1;i++) {
            dp[i+1] = Math.max(dp[i] + nums[i+1], nums[i+1]);
            max = Math.max(dp[i+1], max);
        }
        return max;
    }

    /**
     * 空间优化
     * 容易发现dp[i+1]只和dp[i]和nums[i+1]有关
     * 后面的数之和其前一个数相关，因此可以将复杂度降低到O(1)
     */
    public int maxSubArray2(int[] nums) {
        int len = nums.length;
        /**
         * dp[i] 表示以i为结尾的最大连续子数组的大小
         * 如果nums[i+1]大于零 dp[i+1] = dp[i] + nums[i+1]
         * 如果nums[i+1]小于零 dp[i+1] = nums[i+1]
         */
        //初始化，当i为零的时候，dp[i]等于nums[0]
        int dp = nums[0];
        int max = dp;
        for (int i = 0;i < len - 1;i++) {
            dp = Math.max(dp + nums[i+1], nums[i+1]);
            max = Math.max(dp, max);
        }
        return max;
    }
}
