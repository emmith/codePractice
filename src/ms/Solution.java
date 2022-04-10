package ms;

import java.util.HashMap;
import java.util.Map;

class Solution {

    /**
     * 使用滑动窗口，保持一个长度为R的滑动窗口，从左边向右边移动
     * 用哈希统计货品类型即可
     * 时间复杂度O(n)
     */
    public int solution(int[] A, int R) {
        //数组长度
        int arrLen = A.length;
        //窗口左指针
        int left = 0;
        //窗口右指针
        int right = R - 1;
        //首先用哈希表统计货品的种类,Key为货品，value为货品的数量
        Map<Integer, Integer> map = new HashMap<>();
        //先将0到R-1的放入滑动窗口，故将后边的加入map中
        for (int i = R; i < arrLen; i++) {
            addToMap(map, A[i]);
        }
        int res = 0;
        //开始滑动窗口
        while (left < arrLen - R) {
            res = Math.max(res, map.size());
            //移动窗口
            //释放窗口最左边的元素，将其数量加一中
            addToMap(map, A[left]);
            left++;
            right++;
            //窗口右边新增元素，将其数量减一
            removeFromMap(map, A[right]);
        }
        return res;
    }

    private void addToMap(Map<Integer, Integer> map, int item) {
        map.put(item, map.getOrDefault(item, 0) + 1);
    }

    private void removeFromMap(Map<Integer, Integer> map, int item) {
        //将窗口中的物品数量减一
        map.put(item, map.get(item) - 1);
        //如果物品数量为0了，移除物品
        if (map.get(item) == 0) {
            map.remove(item);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.solution(new int[]{1, 10, 10, 2, 3}, 3));
    }
}
