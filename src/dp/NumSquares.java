package dp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class NumSquares {
    public static int numSquares(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        return dfs(n, map);
    }

    //深度优先遍历
    public static int dfs(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        if (n == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 1; i * i <= n;i++) {
            min = Math.min(min, dfs(n - i * i, map) + 1);
        }
        map.put(n, min);
        return min;
    }

    //广度优先遍历
    public static int bfs(int n) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        queue.offer(n);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            while (size > 0) {
                int k = queue.poll();

                for (int i = 1;i * i <= k;i++) {
                    int j = k - i * i;
                    if (j == 0) {
                        return level;
                    }
                    if (!set.contains(j)) {
                        queue.offer(j);
                        set.add(j);
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(numSquares(60));
    }
}
