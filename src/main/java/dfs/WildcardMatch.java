package dfs;

public class WildcardMatch {
    boolean ans = false;
    int stars = 0;

    private void solve(char[] s, char[] p, int pos1, int pos2) {
        if (ans == true)
            return;
        if (pos1 == s.length && pos2 == p.length)
            ans = true;
        else {
            while (pos1 < s.length && pos2 < p.length && (s[pos1] == p[pos2] || p[pos2] == '?'))//找到第一个不能匹配的地方
            {
                ++pos1;
                ++pos2;
            }
            if (pos1 == s.length && pos2 == p.length)//匹配成功
                ans = true;
            else if (pos2 < p.length && p[pos2] == '*')//不能匹配的地方是*
            {
                while (pos2 < p.length - 1 && p[pos2 + 1] == '*')//连续的*效果相同,跳过,同时--stars
                {
                    --stars;
                    ++pos2;
                }
                --stars;
                int cnt = 0;
                while (s.length - pos1 - cnt + 1 >= p.length - pos2 - stars)//要给p后面的留足空间
                {
                    if ((pos1 + cnt == s.length && pos2 + 1 == p.length) || ((pos1 + cnt < s.length && pos2 + 1 < p.length && s[pos1 + cnt] == p[pos2 + 1]) || (pos2 + 1 < p.length && p[pos2 + 1] == '?')))//下一个能匹配,才尝试
                        solve(s, p, pos1 + cnt, pos2 + 1);
                    ++cnt;
                }
            }
        }
    }

    public boolean isMatch(String s, String p) {
        //统计*的个数
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                stars++;
            }
        }
        solve(s.toCharArray(), p.toCharArray(), 0, 0);
        return ans;
    }
};
