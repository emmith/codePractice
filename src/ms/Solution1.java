package ms;

import java.util.Arrays;

public class Solution1 {
    /**
     * 每一次转账，一家银行增加，另一家减少
     * 如果那家减钱的银行，负债了，我们借钱给他，使其刚好不负债
     * 他向我们借的钱就是他需要的初始资金
     */
    public int[] solution(String R, int[] V) {
        int aAccount = 0;
        int bAccount = 0;
        int aInit = 0;
        int bInit = 0;
        int index = 0;
        for (char ch : R.toCharArray()) {
            if (ch == 'A') {
                //A的账号增加，B的账号减少
                bAccount -= V[index];
                //如果B银行亏钱了，我们借钱给他，使其达到不亏损状态
                if (bAccount < 0) {
                    bInit += Math.abs(bAccount);
                    bAccount = 0;
                }
                aAccount += V[index];
                index++;
            } else if (ch == 'B') {
                //B的账号增加，A的账号减少
                aAccount -= V[index];
                //如果A银行亏钱了，我们借钱给他，使其达到不亏损状态
                if (aAccount < 0) {
                    aInit += Math.abs(aAccount);
                    aAccount = 0;
                }
                bAccount += V[index];
                index++;
            }
        }
        int[] res = new int[2];
        res[0] = aInit;
        res[1] = bInit;
        return res;
    }

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        solution.solution("ABAB", new int[]{10, 5, 10, 15});
    }
}
