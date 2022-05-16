package leetcodezhousai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountIntervals {
    List<int[]> intervals;

    public CountIntervals() {
        intervals = new ArrayList<>();
    }

    public void add(int left, int right) {
        // 为空，直接插入
        if (intervals.isEmpty()) {
            intervals.add(new int[]{left, right});
        }else {
            // 二分找到左边界的位置
            int l = 0;
            int r = intervals.size() - 1;

            while (l < r) {
                int mid = (l + r) / 2;
                if (intervals.get(mid)[0] < left) {
                    l = mid + 1;
                }else {
                    r = mid;
                }
            }
            intervals.add(l + 1, new int[]{left, right});
            for (int i = 0; i < intervals.size() ;i++) {
                System.out.printf("%s", Arrays.toString(intervals.get(i)));
            }
            System.out.println();
        }
    }

    public int count() {
        int sum = 0;
        for (int i = 0; i < intervals.size() ;i++) {
            sum += (intervals.get(i)[1] - intervals.get(i)[0] + 1);
        }
        return sum;
    }

    public void merge() {
    }

    public static void main(String[] args) {
        CountIntervals ci = new CountIntervals();
        ci.add(1, 2);
        ci.add(3, 4);
        ci.add(5,6);
        ci.add(2,3);
    }
}
