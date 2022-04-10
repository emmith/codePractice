package bytedance.search;

public class Search {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }else if (nums[mid] < nums[right]) { //有序，直接二分
                if (target > nums[mid] && target <= nums[right]) {//如果目标值小于右边界
                    left = mid + 1;//目标必然在中点和右边界之间
                }else {
                    right = mid - 1;//
                }
            } else {//无序，找出有序
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                }else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}
