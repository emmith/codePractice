package bytedance.string;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 1){
            return strs[0];
        }
        int resLen = strs[0].length();
        for (int i = 1; i < strs.length;i++) {
            resLen = findCommonPrefix(strs[0], strs[i], resLen);
        }

        return strs[0].substring(0, resLen);
    }

    private int findCommonPrefix(String str1, String str2, int resLen) {
        int i = 0, j = 0;
        while (i < resLen && j < str2.length() && str1.charAt(i) == str2.charAt(j)) {
            i++;
            j++;
        }
        return i;
    }
}
