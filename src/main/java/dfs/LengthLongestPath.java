package dfs;


import java.util.Arrays;

public class LengthLongestPath {


    public int lengthLongestPath(String input) {
        if (input.isBlank()) {
            return 0;
        }

        //先用\n将目录切分，之后用\t来判断当前目录的深度
        String[] words = input.split("\n");
        int[] stack = new int[words.length + 1];

        int ans = 0;
        for (String word : words) {
            //层数从第一层开始(也就是dir，没有\t),层数等于\t的个数加1
            int curDepth = lastTabLevel(word) + 1;
            //减去\t的个数得到长度
            int curLength = word.length() - (curDepth - 1);
            //加1表示加'/'的长度
            stack[curDepth] = stack[curDepth - 1] + 1 + curLength;

            if (word.contains(".")) {
                // 减1表示，去掉文件后面多加的斜杠
                ans = Math.max(ans, stack[curDepth] - 1);
            }
        }

        return ans;
    }

    public static int lastTabLevel(String str) {
        int i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) != '\t') {
                break;
            }
        }
        return i;
    }


    public static void main(String[] args) {
        String test = " \t file.ext";
        System.out.println(lastTabLevel("\t\t\tdir"));
        System.out.println(Arrays.toString("/home////foo/".split("/")));
    }
}
