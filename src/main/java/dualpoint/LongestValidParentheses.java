package dualpoint;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        char []str = s.toCharArray();
        int []memo = new int[s.length()];
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0;i < s.length();i++) {
            if (str[i] == '(') {
                stack.push(i);
            }else {
                //栈中没有左括号了，多余的右括号标记为空
                if(stack.isEmpty()){
                    memo[i] = 1;
                }else {
                    stack.pop();
                }
            }
        }

        //去掉多余的左括号
        while (!stack.isEmpty()) {
            memo[stack.pop()] = 1;
        }

        //统计最长的连续的零的长度，也就是可匹配的括号长度
        int max = 0;
        for (int i = 0, count = 0;i < memo.length;i++) {
            if (memo[i] == 0) {
                count++;
                max = Math.max(max, count);
            }else {
                count = 0;
            }
        }
        return max;
    }

    public int longestValidParentheses2(String s) {
        //dp[i]表示以i为结尾的可匹配括号的最长长度
        int []dp = new int[s.length()];
        char []str = s.toCharArray();
        int max = 0;

        for (int i = 1;i < s.length();i++) {
            if (str[i] == ')') {
                int pre = i - dp[i-1] - 1;
                if (pre >= 0 && str[pre] == '(') {
                    dp[i] = dp[i-1] + 2;
                    if (pre > 0) {
                        dp[i] += dp[pre-1];
                    }
                }
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }
}
