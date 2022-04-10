package bytedance.array;

import java.util.ArrayList;
import java.util.List;

public class SpiralOrder {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (left < right && top < bottom) {
            //遍历上边界
            for (int i = left; i <= right; i++) {
                list.add(matrix[top][i]);
            }
            top++;
            //遍历右边界
            for (int i = top; i <= bottom;i++) {
                list.add(matrix[i][right]);
            }
            right--;
            //遍历下边界
            for (int i = right; i >= left;i--) {
                list.add(matrix[bottom][i]);
            }
            bottom--;
            //遍历左边界
            for (int i = bottom; i >= top;i--) {
                list.add(matrix[i][left]);
            }
            left++;
        }
        while (left <= right && top == bottom) {
            list.add(matrix[top][left++]);
        }

        while (top <= bottom && left == right) {
            list.add(matrix[top++][left]);
        }
        return list;
    }
}
