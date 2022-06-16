package bytedance.number;

/**
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * <p>
 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
 * <p>
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：x = 123
 * 输出：321
 * 示例 2：
 * <p>
 * 输入：x = -123
 * 输出：-321
 * 示例 3：
 * <p>
 * 输入：x = 120
 * 输出：21
 * 示例 4：
 * <p>
 * 输入：x = 0
 * 输出：0
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-integer
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Reverse {
    public int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        long n = reverseToLong(x);
        return n >= Integer.MAX_VALUE ? 0 : (int) n;
    }

    public long reverseToLong(int x) {
        long res = 0;
        //首先去除末尾的零
        while (x % 10 == 0) {
            x /= 10;
        }
        // 反转
        while (x != 0) {
            long dig = x % 10;
            res = res * 10 + dig;
            x = x / 10;
        }
        return res;
    }
}
