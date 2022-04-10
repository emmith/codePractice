package dp;

import java.util.Arrays;

public class MaxProfit {
    /**
     *只要找到当前日子之前的最小值，即可找到利润最大的时候
     * 从左向右遍历记录当前的最小值即可
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int max = Integer.MIN_VALUE;
        for(int i = 0, min = prices[0];i < len;i++) {
            if (prices[i] - min > max) {
                max = prices[i] - min;
            }
            if (min > prices[i]) {
                min = prices[i];
            }
        }
        return max > 0 ? max: 0;
    }

    /**
     *dp[i]的利润为Max(dp[i-1], dp[i-1]+ prices[i] - prices[i-1])
     */
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        int []dp = new int[len];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len-1;i++) {
            dp[i+1] = dp[i]+prices[i+1]-prices[i];
            if (dp[i+1] < 0) {
                dp[i+1] = 0;
            }
            max = Math.max(max, dp[i+1]);
        }
        System.out.println(Arrays.toString(dp));
        return max;
    }

    /**
     * 给定一个数组 prices ，其中 prices[i] 表示股票第 i 天的价格。
     *
     * 在每一天，你可能会决定购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以购买它，然后在 同一天 出售。
     * 返回 你能获得的 最大 利润 。
     *
     *  
     *
     * 示例 1:
     *
     * 输入: prices = [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
     * 示例 2:
     *
     * 输入: prices = [1,2,3,4,5]
     * 输出: 4
     * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
     * 示例 3:
     *
     * 输入: prices = [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int maxProfit3(int[] prices) {
        /*
         * 股票的状态为持有和不持有
         * 持有股票可以理解为买了股票或者持有前一天的股票
         * 同理不持有股票可以理解为卖了股票或者前一天卖了股票
         * 所以可以设dp[i][j]
         * 表示第i天，是否持有股票，j = 1为持有，j等于0为不持有
         */

        int [][]dp = new int[prices.length][2];
        //第一天不持有股票
        dp[0][0] = 0;
        //第一天持有股票
        dp[0][1] = -prices[0];
        for (int i = 1;i < prices.length;i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);
        }
        return dp[prices.length - 1][0];
    }
}
