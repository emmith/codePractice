package dualpoint;

import java.util.Arrays;

public class SortColors {
    public void sortColors(int[] nums) {
        int []num = new int[3];
        for (int i = 0 ;i < nums.length;i++) {
            num[nums[i]]++;
        }
        for(int i = 0,j = 0;i < 3;i++) {
            while(num[i] > 0) {
                nums[j++] = i;
                num[i]--;
            }
        }
    }

    public static void sortColors2(int[] nums) {
        int len = nums.length;
        int left = 0, right = len - 1;

        for (int i = 0;i <= right;) {
            if (nums[i] == 2 ) {
                swap(nums, i, right);
                right--;
            }
            else if (nums[i] == 0) {
                swap(nums, i, left);
                left++;
                i++;
            }
            else if (nums[i] == 1) {
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void swap(int []nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
