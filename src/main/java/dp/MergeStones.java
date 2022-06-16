package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeStones {
    public int mergeStones(int[] stones, int k) {
        int len = stones.length, cp = k;
        while (len != 1) {
            len -= k;
            len++;
            if (len !=1 && len < k) {
                return -1;
            }
        }

        int res = 0;
        List<Integer> list = new ArrayList<>();
        for (int n : stones) {
            list.add(n);
        }
        while (list.size() > 1) {
            int min = Integer.MAX_VALUE;
            int minI = 0;
            int sum = 0;
            for (int i = 0;i <= list.size() - k;i++) {
                sum = 0;
                for (int j = 0;j < k;j++ ) {
                    sum += list.get(j+i);
                }
                if (sum < min) {
                    minI = i;
                    min = sum;
                }
            }
            res += (min == Integer.MAX_VALUE ? 0: min);
            for (int j = 0;j < k && list.size() > 1;j++) {
                if (j == 0) {
                    list.set(minI, min);
                }else {
                    list.remove(1+minI);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        MergeStones ms = new MergeStones();

        System.out.println(ms.mergeStones(new int[] {3,5,1,2,6}, 3));
    }
}
