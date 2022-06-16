package bytedance.search;

import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) throws InterruptedException {
        int[] nums = {1, 1, 1, 1, 3, 4, 5};
        System.out.println(Arrays.toString(nums));

        System.out.println("找左边界-------------------------");
        System.out.println(binarySearchLeftEdge1(nums, 1));
        System.out.println(binarySearchLeftEdge2(nums, 1));

        System.out.println("找右边界-------------------------");
        System.out.println(binarySearchRightEdge2(nums, 6));
        System.out.println(binarySearchRightEdge1(nums, 6));
    }

    // 找左边界
    // {1, 1, 1, 1, 2, 3, 4, 5} 找到最左边的1
    public static int binarySearchLeftEdge1(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + ((right - left) >> 1);

            if (nums[mid] >= k) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.printf("下标 %d ", left);
        return nums[left];
    }

    // 找左边界
    // {1, 1, 1, 1, 2, 3, 4, 5} 找到最左边的1
    public static int binarySearchLeftEdge2(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + ((right - left) >> 1);

            if (nums[mid] < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        System.out.printf("下标 %d ", left);
        return nums[left];
    }

    // 找左边界
    // {1, 1, 1, 1, 2, 3, 4, 5} 找到最右边的1
    public static int binarySearchRightEdge2(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + ((right - left) >> 1);

            if (nums[mid] > k) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.printf("下标 %d ", left);
        return nums[left];
    }

    // 找左边界
    // {1, 1, 1, 1, 2, 3, 4, 5} 找到最右边的1
    public static int binarySearchRightEdge1(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + ((right - left) >> 1) + 1;

            if (nums[mid] > k) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        System.out.printf("下标 %d ", right);
        return nums[right];
    }
}
