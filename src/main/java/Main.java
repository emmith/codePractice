public class Main {

    public static void main(String[] args) {

        String str1 = "abcede";
        String str2 = "nbcdf";

        int len1 = str1.length();
        int len2 = str2.length();
        int ans = 0;

        // dp[i][j] 表示以 str1中的i结尾，和str2中的j结尾的子串的最长公共子串的长度
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        System.out.println(ans);
    }
}

