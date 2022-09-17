package greedy;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.cn/problems/monotone-increasing-digits/
 * 单调递增的数字
 *
 *  找到小于等于n的最大的单调递增的数
 *  单调递增是指对于每一个数位都有 x <= y
 *
 *  n = 332 res = 299
 *  n = 1234 res = 1234
 *  n = 10 res = 9
 */
public class MonotoneIncreasingDigits {
    // 贪心找到第一个不满足 x <= y的位置
    // 找到x最前面和x相等的位置  例如 332   3-2不满足条件 要找到第一个3 把3修改为2 后面补9
    // x -> x - 1 x之后的都修改为9
    public int monotoneIncreasingDigits(int n) {
        char[] nums = Integer.toString(n).toCharArray();

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                // 找到了第一个不满足条件的位置
                while (i > 1 && nums[i - 1] == nums[i - 2]) i--;
                nums[i - 1] -= 1;
                while (i < nums.length) {
                    nums[i++] = '9';
                }
            }
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res = res * 10 + (nums[i] - '0');
        }
        return res;
    }

    // 如果需要严格单调递增呢，必须 x < y
    // 3位数的单调递增的数 220-> 189 330-> 289
    // 将第一个不满足条件的位置减去1，后面改为递增到9的序列
    public int monotoneIncreasingDigits1(int n) {
        char[] nums = Integer.toString(n).toCharArray();

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                // 找到了第一个不满足条件的位置
                while (i > 1 && nums[i - 1] == nums[i - 2]) i--;
                int j = nums.length - 1;
                nums[i - 1] -= 1;
                int c = 9;
                while (j >= i) {
                    nums[j--] = (char)(c-- + '0');
                }
                // 继续往前判断
                // 例如 89799 在上一轮修改后 变成了 88789
                // 此时前两个应该修改为 56 因此继续往前传递
                while (i > 0 && nums[i] <= nums[i - 1]) {
                    nums[i - 1] = (char)(nums[i--] - 1);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0)
            res = res * 10 + (nums[i] - '0');
        }
        return res;
    }

    @Test
    public void test() {
        Assert.assertEquals(monotoneIncreasingDigits1(10), 9);
        Assert.assertEquals(monotoneIncreasingDigits1(100), 89);
        Assert.assertEquals(monotoneIncreasingDigits1(12345), 12345);
        Assert.assertEquals(monotoneIncreasingDigits1(89676), 56789);
        Assert.assertEquals(monotoneIncreasingDigits1(1345512345), 123456789);
        Assert.assertEquals(8, monotoneIncreasingDigits1(8));
        Assert.assertEquals(89, monotoneIncreasingDigits1(114));
        Assert.assertEquals(17, monotoneIncreasingDigits1(17));
        Assert.assertEquals(6789, monotoneIncreasingDigits1(11111));
    }
}
