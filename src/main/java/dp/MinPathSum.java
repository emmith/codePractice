package dp;

/**
 * 64. 最小路径和
 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 *
 * 说明：每次只能向下或者向右移动一步。
 *
 *
 */
public class MinPathSum {
    public int minPathSum(int[][] grid) {
        int width = grid[0].length;
        int height = grid.length;

        //第一列只能往下走
        for (int i = 1;i < height;i++) {
            grid[i][0] += grid[i-1][0];
        }

        //第一行只能往右边走
        for (int i = 1;i < width;i++) {
            grid[0][i] += grid[0][i-1];
        }

        for (int i = 1; i < height;i++) {
            for(int j = 1;j < width;j++) {
                grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
            }
        }
        return grid[height-1][width-1];
    }
}
