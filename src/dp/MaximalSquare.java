package dp;

import utils.PrintUtil;

public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        //dp[i][j]为以当前位置为右下角的正方形的边长
        int [][]dp = new int[height + 1][width + 1];

        int max = 0;

        for (int i = 1;i <= height;i++) {
            for (int j = 1;j <= width;j++){
                if(matrix[i-1][j-1] == '1') {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]);
                    max = Math.max(max, dp[i][j]);
                }
            }
        }

        PrintUtil.printArray(dp);
        return max * max;
    }
}
