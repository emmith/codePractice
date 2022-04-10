package dp;

public class MinDistance {
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        /**
         * dp[i][j]表示word1中的前i个字符好word2中前j个字符的编辑距离
         */
        int[][] dp = new int[len1 + 1][len2 + 1];

        //初始化，dp[0][i]，word1中前零个字符与word2前i个字符的编辑距离为i，同理dp[i][0]为i
        for (int i = 0; i <= len2; i++) {
            dp[0][i] = i;
        }
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1;j <= len2;j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                }else {
                    dp[i][j] = Math.min(Math.min(dp[i][j-1],dp[i-1][j]),dp[i-1][j-1]) + 1;
                }
            }
        }

        return dp[len1][len2];
    }

    //i为word1中的下标，j为word2中的下标
    public static int dfs(String word1, String word2, int i, int j, int[][]memo) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        if (i == 0) {
            return j;
        }
        if (j == 0) {
            return i;
        }
        if (word1.charAt(i-1) == word2.charAt(j-1)) {
            return dfs(word1, word2, i - 1, j - 1, memo);
        }
        int temp1 = dfs(word1, word2, i - 1,j - 1, memo);
        int temp2 = dfs(word1,word2,i-1, j, memo);
        int temp3 = dfs(word1, word2,i, j-1,memo);
        int res =  Math.min(Math.min(temp1, temp2), temp3) + 1;
        memo[i][j] = res;
        return res;
    }

    public static void main(String[] args) {
        String str1 = "";
        String str2 = "rdsfadfsadfsfsfsdafafdsfa";
        long cur = System.currentTimeMillis();
        int res1 = minDistance(str1, str2);
        long point1 = System.currentTimeMillis();
        int res2 = dfs(str1, str2,str1.length(),str2.length(), new int[str1.length()+1][str2.length()+1]);
        long point2 = System.currentTimeMillis();
        System.out.println( "res1 "+ res1 + "  " + (point1- cur));
        System.out.println( "res2 "+ res2 + "  " + (point2- point1));
    }
}
