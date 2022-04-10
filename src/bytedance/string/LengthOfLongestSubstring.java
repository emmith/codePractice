package bytedance.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0;
        char []str = s.toCharArray();
        Set<Character> set = new HashSet<>();
        int len = s.length();
        int max = 0;

        while (right < len) {
            if (!set.contains(str[right])) {
                max = Math.max(max, right - left + 1);
                set.add(str[right]);
                right++;
            }else {
                set.remove(str[left]);
                left++;
            }
        }
        return max;
    }
}
