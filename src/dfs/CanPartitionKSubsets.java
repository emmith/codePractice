package dfs;

import java.util.Arrays;

public class CanPartitionKSubsets {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {
            sum += num;
            max = Math.max(max, num);
        }

        if (sum % k != 0 || max > sum / k) {
            return false;
        }
        boolean[] flag = new boolean[nums.length];
        int target = sum / k;

        return dfs(nums, flag, target, k, 0, 0);
    }

    public boolean dfs(int[] nums, boolean[] flag, int target, int k, int start, int bucket) {
        if (k == 0) {//分成了K个部分
            return true;
        }

        if (bucket == target) {//找下一个等于target的部分
            return dfs(nums, flag, target, k - 1, 0, 0);
        }

        for (int i = start; i < nums.length; i++) {
            if (!flag[i] && bucket + nums[i] <= target) {
                bucket = bucket + nums[i];
                flag[i] = true;

                if (dfs(nums, flag, target, k, i + 1, bucket)) {
                    return true;
                }

                flag[i] = false;
                bucket = bucket - nums[i];

                if (bucket == 0) break;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = {3, 3, 10, 2, 6, 5, 10, 6, 8, 3, 2, 1, 6, 10, 7, 2};
        CanPartitionKSubsets canPartitionKSubsets = new CanPartitionKSubsets();

        System.out.println(canPartitionKSubsets.canPartitionKSubsets(nums, 6));
    }
}
