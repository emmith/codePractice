package dualpoint;

import java.util.Arrays;

public class MoveZeroes {
    public static void moveZeroes(int[] nums) {
        int left = 0, right = 1;
        int len = nums.length;
        while (right < len) {
            if (nums[left] == 0 && nums[right] != 0) {
                nums[left] = nums[right];
                nums[right] = 0;
                left++;
                right++;
            }
            if (right == len) {
                break;
            }
            if (nums[left] != 0) {
                left++;
                right++;
            } else if (nums[right] == 0) {
                right++;
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes2(int[] nums) {
        int j = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (nums[i] != 0) {
                nums[j++] = nums[i];
            }
        }

        for(int i = j;i < len;i++) {
            nums[i] = 0;
        }

        System.out.println(Arrays.toString(nums));
    }
}
