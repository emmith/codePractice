package leetcodezhousai;

import java.util.Arrays;

public class Solution2 {
    public int maxConsecutive(int bottom, int top, int[] special) {
        // 先对特殊楼层排序
        Arrays.sort(special);

        int ans = 0;

        for (int left = bottom, right = 0 ; left <= top && right < special.length ;) {
            if (left < special[right]) {
                ans = Math.max(special[right] - left, ans);
            }
            left = special[right] + 1;
            right++;
        }
        return Math.max(ans, top - special[special.length - 1]);
    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] num = {7,6,8};
        System.out.println(solution2.maxConsecutive(6, 8, num));
    }
}
