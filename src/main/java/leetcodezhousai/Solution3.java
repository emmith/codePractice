package leetcodezhousai;

import java.util.Arrays;

public class Solution3 {
    int[] res = new int[32];
    public int largestCombination(int[] candidates) {

        for (int i : candidates) {
            countBit(i);
        }
        return Arrays.stream(res).max().getAsInt();
    }

    public void countBit(int num) {
        int i = 0;
        while (num != 0) {
            res[i] += (num & 1);
            i++;
            num = num >> 1;
        }
    }

    public static void main(String[] args) {
        int[] test = {16,17,71,62,12,24,14};
        Solution3 solution3 = new Solution3();
        System.out.println(solution3.largestCombination(test));
    }
}
