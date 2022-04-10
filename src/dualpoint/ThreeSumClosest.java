package dualpoint;

import java.util.Arrays;

public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        //先排序
        Arrays.sort(nums);

        int len = nums.length;
        int result = 10000;
        for (int i = 0;i < len - 2;i++) {
            int left = i + 1, right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(result - target)) {
                    result = sum;
                }
                if (sum > target) {
                    right--;
                }else if (sum < target){
                    left++;
                }else {
                    return sum;
                }
            }
        }
        return result;
    }
}
