package bytedance.array;

import cn.hutool.core.util.RandomUtil;
import utils.PrintUtil;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class FindKthLargest {
    public int findKthLargest(int nums[], int k) {
        int target = nums.length - k;
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int pivot = partition(nums, low, high);
            if (pivot == target) {
                return nums[pivot];
            }else if (pivot < target) {
                low = pivot + 1;
            }else {
                high = pivot - 1;
            }
        }
        return nums[low];
    }


    public int partition(int []nums, int low ,int high) {
        int i = RandomUtil.randomInt(low, high + 1);
        int pivot = nums[i];
        int temp = nums[i];
        nums[i] = nums[low];
        nums[low] = temp;

        while (low < high) {
            while (low < high && nums[high] >= pivot) {
                high--;
            }
            nums[low] = nums[high];
            while (low < high && nums[low] <= pivot) {
                low++;
            }
            nums[high] = nums[low];
        }
        nums[low] = pivot;
        PrintUtil.printArray(nums);
        return low;
    }
}
