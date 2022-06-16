package DataStructure.mindistance;

import java.util.Arrays;

public class Dijkstra {
    public int[] dijkstra(int[][] matrix) {
        int[] dist = Arrays.copyOf(matrix[0], matrix[0].length);

        //每一轮选出最短路径
        int min = Integer.MAX_VALUE;
        int minI = 0;

        for (int i = 0; i < matrix[0].length; i++) {
            dist[i] = matrix[0][i];
            if (dist[i] < min) {
                min = dist[i];
                minI = i;
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            //用minI去更新其他的值
            for (int j = 0; j < matrix.length; j++) {
                if (j != minI) {
                    dist[j] = Math.min(dist[j], dist[minI] + matrix[minI][j]);
                    if (dist[j] < min) {
                        min = dist[i];
                        minI = i;
                    }
                }
            }
            min = Integer.MAX_VALUE;
        }
        return dist;
    }
}
