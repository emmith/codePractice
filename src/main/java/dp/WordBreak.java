package dp;

import utils.PrintUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        //将list转为set，便于匹配
        Set<String> set = wordDict.stream().collect(Collectors.toSet());

        //dp[i]表示s的前i个字符能否被wordDict中的元素拆分
        boolean []dp = new boolean[s.length()+1];
        dp[0] = true;

        for (int i = 1;i <= s.length();i++) {
            for (int j = 0;j < i;j++) {
                if (dp[j] && set.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        PrintUtil.printArray(dp);
        return dp[s.length()];
    }
}
