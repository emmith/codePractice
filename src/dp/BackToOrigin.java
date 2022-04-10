package dp;

/**
 * 圆环上有10个点，编号为0~9。从0点出发，每次可以逆时针和顺时针走一步，问走n步回到0点共有多少种走法。
 * <p>
 * 输入: 2
 * 输出: 2
 * 解释：有2种方案。分别是0->1->0和0->9->0
 */
public class BackToOrigin {
    //走n步数，有多少种回到原地点的方式
    public int backToOrigin(int n) {
        //假设数组长度为10
        int length = 10;

        //dp[i][j] 表示走i步数到j有多少种走法
        int[][] dp = new int[n][length];

        //从0点走0步到0点只有一种走法，就是不动
        dp[0][0] = 1;

        for (int i = 1; i < n;i++) {
            for (int j = 0; j < length;j++) {
                //走到j可以从j-1位置走一步到j，也可以从j+1走回j
                //走i步到j，可以先走i-1步到j-1或者先走i-1步到j+1
                //两种之和为走i步到j的方法数
                dp[i][j] = dp[i-1][(j-1+length) % length] + dp[i-1][(j+1+length) % length];
            }
        }
        return dp[n][0];
    }
}
