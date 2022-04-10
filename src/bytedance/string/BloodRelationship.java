package bytedance.string;

import java.util.Scanner;

public class BloodRelationship {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        String[] dna = input.split(",");

        //dp[i][j]表示dna1的前i个字符变为dna2的前j个字符需要多少步骤
        int[][] dp = new int[dna[0].length() + 1][dna[1].length() + 1];

        //初始化
        for (int i = 0; i <= dna[0].length(); i++) {
            dp[i][0] = i;
        }

        for (int i = 0; i <= dna[1].length(); i++) {
            dp[0][i] = i;
        }

        for (int i = 1; i <= dna[0].length(); i++) {
            for (int j = 1; j <= dna[1].length(); j++) {
                if (dna[0].charAt(i - 1) == dna[1].charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        System.out.println(dp[dna[0].length()][dna[1].length()]);
    }
}
