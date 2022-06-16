package bytedance.array;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[2];

        for (int i = 0; i < nums.length; i++) {
            //将target - nums[i]放入map中，如果遇到了等于target - nums[i]的数，代表找到了
            if (map.containsKey(nums[i])) {
                res[0] = map.get(nums[i]);
                res[1] = i;
                break;
            }else {
                map.put(target - nums[i], i);
            }
        }
        return res;
    }
}
