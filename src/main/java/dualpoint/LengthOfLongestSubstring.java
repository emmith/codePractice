package dualpoint;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *  
 *
 * 提示：
 *
 * 0 <= s.length <= 5 * 104
 * s 由英文字母、数字、符号和空格组成
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        int right = -1;
        int result = 0;
        int len = s.length();
        Set<Character> set = new HashSet<>();

        for (int left = 0;left < len; left++) {
            //两端相同时，移除左边的字母
            if (left != 0) {
                set.remove(s.charAt(left - 1));
            }

            //如果字母不在哈希表中，右边指针向右移动，直到相同
            while (right + 1 < len && !set.contains(s.charAt(right+1))) {
                set.add(s.charAt(right+1));
                right++;
            }

            result = Math.max(result, right - left + 1);
        }
        return result;
    }
}
