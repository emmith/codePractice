package binarysearch;

public class LeftRightEdgeSearch {


    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 6, 6, 9, 9};
        int n = nums.length;
        int left = searchForLeftEdge(nums, n - 1, 2);
        System.out.println(left + " " + nums[left]);
        int right = searchForRightEdge(nums, n - 1, 2);
        System.out.println(right + " " + nums[right]);
    }

    // 寻找左边界
    // 求大于某个数的最小的数的下标 or 等于某个数的最左边的数
    // 比如 1 1 2 2 2 3 3 5 5 6 6
    // 如果查2，会返回最左边的2
    // 如果查4，会返回最左边的5的下标
    // 原理很简单，就是找到这个数以后，让右边界等于mid，继续往左找
    private static int searchForLeftEdge(int[] nums, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = (left + right) >> 1;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 寻找右边界
    private static int searchForRightEdge(int[] nums, int right, int target) {
        int left = 0;
        while (left < right) {
            int mid = right - ((right - left) >> 1);
            if (nums[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
