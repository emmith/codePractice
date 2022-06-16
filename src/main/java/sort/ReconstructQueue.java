package sort;

import utils.PrintUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReconstructQueue {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (int[] a, int[] b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
        List<int []> list = new ArrayList<>();

        for (int [] a : people) {
            list.add(a[1], a);
        }
        return list.toArray(new int[list.size()][]);
    }
}
