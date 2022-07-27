package dfs;

/**
马踏棋盘问题 暴力DFS
思路：
用深度优先遍历+回溯法
1，初始化一个8*8的矩阵，元素都为1
2，设定马的起始位置（x,y),对走过的节点做标记
3，对起始位置的下个一位置的8种可能，做循环操作，若没有路可走（也就是一条路走到头，发现返回结果为false），则开始进行回溯
4，在循环到的节点重复步骤2，3（也就是循环中用递归）。

要找到可能的线路，而不是简单的遍历所有，不能用广度优先遍历
*/
public class HorseOnChessboard {
    static int[][] matrix = new int[8][8];
    static int[][] flag = new int[8][8];

    public static void createMatrix() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = 0;
                flag[i][j] = 0;
            }
        }
    }

    static int[] step1 = {1, 1, -1, -1, 2, 2, -2, -2};
    static int[] step2 = {2, -2, 2, -2, 1, -1, 1, -1};

    public static boolean check(int x, int y) {
        if (x > 7 || x < 0 || y > 7 || y < 0 || flag[x][y] == 1)
            return false;
        return true;
    }

    public static boolean dfs(int x, int y, int step) {
        flag[x][y] = 1;
        matrix[x][y] = step;
        if (step == 64) {
            print();
            return true;
        }
        for (int i = 0; i < 8; i++) {
            if (check(x + step1[i], y + step2[i])) {
                boolean result = dfs(x + step1[i], y + step2[i], step + 1);//参数局部变量，并不对x做改变
                if (result == true)//一直走到最后，若满足条件输出，若不满足回溯
                    return true;
            }
        }
        //回溯
        flag[x][y] = 0;
        matrix[x][y] = 0;
        return false;
    }

    public static void print() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        createMatrix();
        boolean f = dfs(7, 7, 1);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
        System.out.println(f);
    }
}
