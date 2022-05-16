package leetcodezhousai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution1 {
    public List<String> removeAnagrams(String[] words) {
        Map<String, Integer> map = new HashMap<>();
        List<String> res = new ArrayList<>();

        for (int i = 0; i < words.length ;i++) {
            String hash = hash(words[i]);
            if (map.containsKey(hash) && i == map.get(hash) + 1) {
                map.put(hash, map.get(hash) + 1);
                continue;
            }else {
                map.put(hash(words[i]), i);
                res.add(words[i]);
            }

        }
        return res;
    }

    private String hash(String str) {
        int[] hash = new int[26];
        for (int i = 0; i < str.length(); i++) {
            hash[str.charAt(i) - 'a']++;
        }

        StringBuilder res = new StringBuilder(26);
        for (int i : hash) {
            res.append(i);
        }

        return res.toString();
    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        String[] test = {"abba","baba","bbaa","cd","cd","s","a","a","s"};
        System.out.println(solution1.removeAnagrams(test));
    }
}