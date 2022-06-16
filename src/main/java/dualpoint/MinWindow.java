package dualpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * <p>
 *  
 * <p>
 * 注意：
 * <p>
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 * 示例 2：
 * <p>
 * 输入：s = "a", t = "a"
 * 输出："a"
 * 示例 3:
 * <p>
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length, t.length <= 105
 * s 和 t 由英文字母组成
 *  
 * <p>
 * 进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-window-substring
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class MinWindow {
    public static String minWindow(String s, String t) {
        Map<Character, Integer> s_map = new HashMap<>();
        Map<Character, Integer> t_map = countCharacters(t);

        int t_len = t.length();
        int s_len = s.length();
        int right = 0, left = 0;
        int count = 0;
        String res = "";
        Integer res_len = Integer.MAX_VALUE;
        while (right < s_len) {
            char r_ch = s.charAt(right);
            s_map.put(r_ch, s_map.getOrDefault(r_ch, 0) + 1);
            if (t_map.containsKey(r_ch) && s_map.get(r_ch) <= t_map.get(r_ch)) {
                count++;
            }
            char l_ch = s.charAt(left);
            while (s_map.get(l_ch) > t_map.getOrDefault(l_ch, 0)){
                s_map.put(l_ch, s_map.get(l_ch) - 1);
                if (++left < right) {
                    l_ch = s.charAt(left);
                }
            }
            if (count == t_len) {
                if (res_len > right - left + 1) {
                    res = s.substring(left, right+1);
                    res_len = res.length();
                }
            }
            right++;
        }
        return res;
    }


    //统计字符串中的字符，将其存储到哈希表中
    public static Map<Character, Integer> countCharacters(String str) {
        int len = str.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        return map;
    }

    public static String minWindow2(String s, String t) {
        int []s_memo = new int[200];
        int []t_memo = new int[200];
        int t_len = t.length();

        //统计t中字符的数量
        for (int i = 0;i < t_len;i++) {
            t_memo[t.charAt(i)]++;
        }

        int s_len = s.length();
        int left = 0, right = 0;
        int count = 0;
        String res = "";
        Integer res_len = Integer.MAX_VALUE;

        while (right < s_len) {
            char r_ch = s.charAt(right);
            s_memo[r_ch]++;

            if (t_memo[r_ch] != 0 && s_memo[r_ch] <= t_memo[r_ch]) {
                count++;
            }
            for (char l_ch = s.charAt(left); s_memo[l_ch] > t_memo[l_ch];) {
                s_memo[l_ch]--;
                left++;
                if (left < right) {
                    l_ch = s.charAt(left);
                }
            }

            if (count == t_len) {
                if (res_len > right - left + 1) {
                    res = s.substring(left, right + 1);
                    res_len = res.length();
                }
            }
            right++;
        }
        return res;
    }
}
