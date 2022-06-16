package bytedance.search;

public class Search {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[right]) { //右边更长
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;//目标必然在中点和右边界之间
                } else {
                    right = mid - 1;//
                }
            } else {//左边更长
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }
        return -1;
    }


    // 包含重复元素
    public int search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < nums[right]) { //右边更长
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;//目标必然在中点和右边界之间
                } else {
                    right = mid - 1;//
                }
            } else if (nums[mid] > nums[right]) {//左边更长
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                right--;
            }
        }
        return -1;
    }

}
