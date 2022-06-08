package leetcodezhousai;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CountIntervals {

    List<int[]> intervals;

    int ans = 0;

    //用于统计数组中的有效区间数
    int size;

    public CountIntervals() {
        intervals = new ArrayList<>();
    }

    public void add(int left, int right) {
        if (intervals.isEmpty()) {
            intervals.add(new int[]{left, right});
            ans += (right - left + 1);
            size++;
        } else
            merge(intervals, left, right);
//        for (int[] ele : intervals) {
//            System.out.printf("%s", Arrays.toString(ele));
//        }
//        System.out.printf("   insert[%d, %d]", left, right);
//        System.out.printf("   size : %d", size);
//        System.out.println();

    }

    public int count() {
        return ans;
    }

    public void merge(List<int[]> intervals, int left, int right) {
        // 先用二分查找判断是否需要合并
        int L = binarySearch1(intervals, right);
        System.out.println(binarySearch(intervals, right) == binarySearch1(intervals, right));
        int l_temp = left;
        int r_temp = right;

        // 子区间直接return
        if (L != -1 && intervals.get(L)[1] >= right && intervals.get(L)[0] <= left) {
            return;
        }

        int end = L;
        // count等于0,表示没有合并，就是孤儿区间
        int count = 0;

        // 找到可以合并的最长区间
        // 加1,是为了合并[1,2],[3,4]这种情况
        while (L != -1 && intervals.get(L)[1] + 1 >= l_temp) {
            l_temp = Math.min(intervals.get(L)[0], l_temp);
            r_temp = Math.max(r_temp, intervals.get(L)[1]);
            // 减去被合并的区间中的整数个数
            ans -= (intervals.get(L)[1] - intervals.get(L)[0] + 1);
            count++;
            L--;
        }

        // 插入孤儿区间（和其他区间没有公共元素），更新数组的情况
        if (count == 0) {
            //插入末尾时，数组中有空闲的空间，直接复用
            if (L + 1 == size && L + 1 < intervals.size()) {
                intervals.get(L + 1)[0] = l_temp;
                intervals.get(L + 1)[1] = r_temp;
            } else// 插入开头or中间or末尾，数组中没有空闲空间了，开辟新的空间
                intervals.add(L + 1, new int[]{l_temp, r_temp});
            size++;
        } else {// 插入非孤儿区间，数组一定有空闲空间，直接复用
            intervals.get(L + 1)[0] = l_temp;
            intervals.get(L + 1)[1] = r_temp;

            //如果合并了超过一个区间，需要将后面的区间往前面移动
            //移动后，后面有空闲空间，可以用于之后插入新的孤儿区间时复用
            if (count > 1) {
                for (int i = end + 1; i < size; i++) {
                    intervals.get(i - count + 1)[0] = intervals.get(i)[0];
                    intervals.get(i - count + 1)[1] = intervals.get(i)[1];
                }
                size -= count - 1;
            }
        }
        // 加上合并后的区间的整数个数
        ans += (r_temp - l_temp + 1);
    }

    // 二分查找，找到小于目标的最大区间
    //例如[1,2],[6,7]中查找 4 会返回 [1,2]的下标0
    private int binarySearch(List<int[]> intervals, int target) {
        int l = 0;
        int r = size - 1;

        // 确保返回的下标的选项一定小于target
        /**
         * while (left < right) {
         *             int mid = ((right - left) >> 1) + left;
         *
         *             if (nums[mid] < target) {
         *                 left = mid + 1;
         *             }else
         *                 right = mid ;
         * }
         * 这种方法会返回，离他最近的大于它的数
         * 当然边界要特殊处理
         */
        while (l < r) {
            int mid = (l + r) / 2 + 1;
            if (intervals.get(mid)[0] > target) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }

        // 处理边界值，也就是[[5,6]]中搜 2 这种情况，加一为了处理4这个数字
        // 因为4可以和5合并
        return intervals.get(l)[0] > target + 1 ? -1 : l;
    }

    // 二分查找，找到小于目标的最大区间
    //例如[1,2],[6,7]中查找 4 会返回 [1,2]的下标0
    private int binarySearch1(List<int[]> intervals, int target) {
        int l = 0;
        int r = size - 1;

        // 确保返回的下标的选项一定小于target
        while (l <= r) {
            int mid = ((r - l) >> 1) + l;
            if (intervals.get(mid)[0] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        if (r < 0) {
            return r;
        }

        if (r == size) {
            return size - 1;
        }

        // 处理边界值，也就是[[5,6]]中搜 2 这种情况，加一为了处理4这个数字
        // 因为4可以和5合并
        return intervals.get(r)[0] > target + 1 ? -1 : r;
    }

    @Test
    void test1() {
        CountIntervals ci = new CountIntervals();
        ci.add(1, 2);
        ci.add(913, 936);
        ci.add(4, 5);
        ci.add(4, 936);
        ci.add(999, 1000);
        System.out.println(ci.count());

    }

    @Test
    void test2() {
        CountIntervals ci = new CountIntervals();
        ci.add(811, 882);
        ci.add(49, 149);
        System.out.println(ci.count());
        ci.add(967, 999);
        ci.add(406, 589);
        ci.add(928, 966);
        ci.add(911, 976);
        ci.add(930, 993);
        System.out.println(ci.count());
        ci.add(669, 727);
        ci.add(752, 949);
        ci.add(646, 952);
        ci.add(152, 431);
        System.out.println(ci.count());
    }

    @Test
    void test3() {
        CountIntervals ci = new CountIntervals();
        ci.add(99, 100);
        ci.add(97, 98);
        System.out.println(ci.count());
        ci.add(95, 96);
        System.out.println(ci.count());
    }
}

/**
 * Your CountIntervals object will be instantiated and called as such:
 * CountIntervals obj = new CountIntervals();
 * obj.add(left,right);
 * int param_2 = obj.count();
 */