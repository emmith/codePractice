package dualpoint;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    public int[] intersection(int[] nums1, int[] nums2) {
        int[] memo = new int[1001];
        List<Integer> list = new ArrayList<>();
        for (int i : nums1) {
            if (memo[i] == 0) {
                memo[i]++;
            }
        }
        for (int i : nums2) {
            memo[i]++;
            if (memo[i] == 2) {
                list.add(i);
            }
        }
        int[] res = new int[list.size()];
        int index = 0;
        for (int i : list) {
            res[index++] = i;
        }
        return res;
    }
}
