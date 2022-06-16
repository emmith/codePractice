package dp;

public class LongestPalindrome {
    public String longestPalindrome(String s) {
        int len = s.length();
        char[] str = s.toCharArray();
        int[] range = new int[3];
        for (int i = 0; i < s.length(); i++) {
            i = findLongest(str, i, range);
            //如果当前位置到最右边的距离的两倍小于当前最长回文串的长度，停止循环
            if (len - i + len - i < range[2]) {
                break;
            }
        }
        return s.substring(range[0], range[1] + 1);
    }

    public int findLongest(char[] str, int low, int[] range) {
        int high = low;
        int len = str.length;
        /*
        为了一次性处理奇数和偶数长度的回文数，我们先把中间的部分找到
        例如aba  abba abbba
        我们先把中间的b找到这样，这样可以统一处理奇偶
         */
        while (high + 1 < len && str[high] == str[high + 1]) {
            high++;
        }

        int res = high;
        //从中间向两边扩散，寻找最长的回文串
        while (low > 0 && high < len - 1 && str[low - 1] == str[high + 1]) {
            low--;
            high++;
        }

        int distance = high - low;
        if (distance > range[1] - range[0]) {
            range[0] = low;
            range[1] = high;
            range[2] = distance;
        }
        return res;
    }

    /**
     * dp[i][j]表示从下标i到j是否为回文串
     * dp[i][j] = dp[i+1][j-1] && s[i] == s[j]
     */
    public String longestPalindromeDp(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        char[] str = s.toCharArray();

        int left = 0;
        int maxLen = 1;

        for (int d = 1; d < len; d++) {
            for (int l = 0; l + d < len; l++) {
                int r = l + d;
                if (str[l] != str[r]) {
                    dp[l][r] = 0;
                } else {
                    if (d < 3) {
                        dp[l][r] = 1;
                    } else {
                        dp[l][r] = dp[l + 1][r - 1];
                    }
                    if (dp[l][r] == 1) {
                        if (d + 1 > maxLen) {
                            left = l;
                            maxLen = d + 1;
                        }
                    }
                }

            }
        }

        return s.substring(left, left + maxLen);
    }
}
