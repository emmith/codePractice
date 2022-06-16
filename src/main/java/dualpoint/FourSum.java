package dualpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 * <p>
 * 0 <= a, b, c, d < n
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 * <p>
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/4sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FourSum {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        int len = nums.length;
        //先排序
        Arrays.sort(nums);

        for (int i = 0; i < len - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {//去重
                continue;
            }
            long min1 = (long)nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (min1 > target) {//最小值比目标值大，直接终止循环
                break;
            }
            long max1 = (long)nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3];
            if (max1 < target) {//最大值比目标值小，直接到下一个大的数
                continue;
            }
            for (int k = i + 1; k < len - 2; k++) {
                if (k > i + 1 && nums[k] == nums[k - 1]) {//去重
                    continue;
                }
                long min2 = (long)nums[i] + nums[k] + nums[k + 1] + nums[k + 2];
                if (min2 > target) {//最小值比目标值大，直接终止循环
                    break;
                }
                long max2 = (long)nums[i] + nums[k] + nums[len - 2] + nums[len - 1];
                if (max2 < target) {//最大值比目标值小，直接到下一个大的数
                    continue;
                }

                int left = k + 1;
                int right = len - 1;

                while (left < right) {
                    int sum = nums[i] + nums[k] + nums[left] + nums[right];
                    if (sum == target) {
                        list.add(Arrays.asList(nums[i], nums[k], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left+1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right-1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }else if (sum < target) {
                        left++;
                    }else {
                        right--;
                    }
                }
            }
        }
        return list;
    }
}
