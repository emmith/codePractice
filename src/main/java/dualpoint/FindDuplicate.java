package dualpoint;

public class FindDuplicate {
    /**
     * 二分查找法
     *时间复杂度O(nlogn)
     * 题目给出数组中的元素都处于(1,n)之间，只有一个数出现了两次，
     *如果我们取n/2为中点，遍历数组，如果小于n/2的数的数量大于n/2
     * 则这个数在n/2以下，否则在n/2以上
     */
    public int findDuplicate(int[] nums) {
        int len = nums.length;
        int left = 0, right = len - 1;

        while (left < right) {
            int count = 0;
            int mid = (left + right) >> 1;

            for (int i : nums) {
                if (i <= mid) {
                    count++;
                }
            }

            if (count <= mid) {
                left = mid+1;
            }else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 快慢指针
     * @param nums
     * @return
     */
    public int findDuplicate2(int[] nums) {
        int slow = 0;
        int fast = 0;

        while (slow != fast || fast == 0) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        slow = 0;
        while (slow != fast || slow == 0) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
