package ms;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

// 微软 8.26第一题最优解思路
// 求字符串中所有字母都是出现偶数次的最长子串 字母都是小写字母
// 字符串最大长度 100000
// 暴力枚举每个子串为On^2会超时
// 偶数次出现，如果给字母编码，这样的子串异或的值一定是零
// a -> 1
// b ->10
// c -> 100
// ....这样每一个字母代表一个二进制位，用一个26位的整数，就可以表示出所有状态
// 用前缀异或记录每一个前缀的状态，相同状态之差中间必为偶数个字母
public class Solution1 {
    @Test
    public void solution() {
        int[] map = new int[26];
        // 编码，给每个字母一个互斥的状态
        for (int i = 0; i < map.length; i++) {
            map[i] = (1 << i);
        }

        String str = "acddeebabab";
        int res = 0;
        Map<Integer, Integer> preMap = new HashMap<>();
        // 用于处理前缀就是目标子串的情况
        preMap.put(0, -1);
        int ans = 0;

        for (int i = 0; i < str.length(); i++) {
            int idx = str.charAt(i) - 'a';
            res ^= map[idx];
            // 拥有相同的状态
            if (preMap.containsKey(res)) {
                ans = Math.max(ans, i - preMap.get(res));
            } else {
                // 从左往右遍历，只需要记录第一个状态
                preMap.put(res, i);
            }
        }
        System.out.println(ans);
    }
}
