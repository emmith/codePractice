package dualpoint;

import java.util.Arrays;

public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        int left = 0, right = left;
        while (right < len) {
            while (right + 1 < len && nums[right] == nums[right+1]) {
                right++;
            }
            nums[left++] = nums[right++];
        }
        System.out.println(Arrays.toString(nums));
        return left;
    }
}
