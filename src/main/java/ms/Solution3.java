package ms;

import java.util.Arrays;

class Solution3 {
    /**
     * 暴力枚举所以子串进行比较
     */
    public int solution(String A, String B) {
        int N = A.length();
        int res = 0;

        //枚举所以字符串
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j <= N; j++) {
                if (strToKey(A.substring(i, j)).equals(strToKey(B.substring(i, j)))) {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 将字符串按字符数量转为唯一的哈希键值
     * 比如ABBA 会变成2200000.....
     * 对应字符数量一致的字符串会拥有相同的键值
     */
    private static String strToKey(String str) {
        //作为哈希表，统计对应字符数量，A对应0的下标，依次类推
        int[] res = new int[80];
        for (char ch : str.toCharArray()) {
            res[ch - 'A']++;
        }
        //将统计后的字符的数量，按字母表顺序组成字符串
        StringBuilder resStr = new StringBuilder(80);
        for (int i : res) {
            resStr.append(i);
        }
        return resStr.toString();
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        System.out.println(solution3.solution("dBacaAA", "caBdaaA"));
        System.out.println(solution3.solution("ZZXYOz", "OOYXZZ"));
        System.out.println(solution3.solution("a", "b"));
    }
}

