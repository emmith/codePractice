package bytedance.dualpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        int len = nums.length;
        //首先排序
        Arrays.sort(nums);

        //先选定第一个数，下标为i，范围到len - 2
        for (int i = 0;i < len - 2;i++) {
            int curMax = nums[i] + nums[len-1] + nums[len-2];
            if (curMax < 0) {//最大值小于0，直接下一位
                continue;
            }
            int curMin = nums[i] + nums[i+1] + nums[i+2];
            if (curMin > 0) {//最小值大于0，终止循环
                break;
            }
            //去重
            if (i > 0 && nums[i-1] == nums[i]) {
                continue;
            }
            //第二个数
            int left = i + 1;
            //第三个数
            int right = len - 1;
            //使用双指针，寻找后两位数
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    list.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    //三数之和
                    while (left < right && nums[left] == nums[left+1]) {
                        left++;
                    }
                    while (left < right  && nums[right-1] == nums[right]) {
                        right--;
                    }
                    left++;
                    right--;
                }else if (sum < 0) {
                    left++;
                }else {
                    right--;
                }
            }

        }
        return list;
    }
}
