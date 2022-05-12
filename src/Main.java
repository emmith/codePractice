public class Main {
    /**
     * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     * <p>
     * 你需要按照以下要求，给这些孩子分发糖果：
     * <p>
     * 每个孩子至少分配到 1 个糖果。
     * 相邻两个孩子评分更高的孩子会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
     * <p>
     *  
     * <p>
     * 示例 1：
     * <p>
     * 输入：ratings = [1,0,2]
     * 输出：5
     * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
     * 示例 2：
     * <p>
     * 输入：ratings = [1,2,2]
     * 输出：4
     * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
     * 第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
     *  
     * <p>
     * 提示：
     * <p>
     * n == ratings.length
     * 1 <= n <= 2 * 104
     * 0 <= ratings[i] <= 2 * 104
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/candy
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static int candy(int[] ratings) {
        int res = 0;
        //山顶
        int top = 0;

        for (int i = 0; i < ratings.length; ) {
            int j = i + 1;
            while (j < ratings.length && ratings[j] > ratings[j - 1]) {
                j++;
            }
            //第一个山峰在下标j-1
            //左边递增序列的长度
            int leftLen = j - i;
            while (j < ratings.length && ratings[j - 1] > ratings[j]) {
                j++;
            }
            //右边递减序列的长度
            int rightLen = j - i - leftLen + 1;
            //首项
            int first = 1;
            //如果左边山底还有更小的数，首项从2开始
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                first = 2;
            }
            //山顶元素大小取决于两边序列的长度，由于左边序列可能从2开始，所有计为len1+first-1
            top = Math.max(leftLen + first - 1, rightLen);
            /*
            左边序列为(first(首项) + first+leftLen-2(末项)) * (leftLen-1)(项数) / 2
            右边序列为(1(首项) + rightLen-1(末项)) * (rightLen-1)(项数) / 2
             */
            res += (top + ((leftLen + first * 2 - 2) * (leftLen - 1)  + rightLen * (rightLen - 1)) / 2);
            //找下一个山峰
            i = j;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
    }

    private static int binSearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = ((right - left) >> 1) + left;

            if (nums[mid] < target) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }

        System.out.println("l  " + left);
        System.out.println("r  " + right);

        return nums[left] == target ? left : -1;
    }

    private static int bitCount(int num) {
        int count = 0;
        while (num != 0) {
            count += (num % 2);
            num = num >> 1;
        }
        return count;
    }

}
