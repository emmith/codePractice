package DataStructure.heap;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KSum {
    @Test
    public void test() {
        int[] nums = {1, 2, 3};
        long res = kSum(nums, 5);
        System.out.println(res);
    }

    public long kSum(int[] nums, int k) {
        PriorityQueue<Dot> queue = new PriorityQueue<>((Dot a, Dot b) -> {
            return Long.compare(a.value, b.value);
        });

        // 最大子序列和，也就是所有正数的和
        long maxSum = 0;
        for (int num : nums) {
            if (num > 0) {
                maxSum += num;
            }
        }

        // 负数变为正数
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) nums[i] = -nums[i];
        }

        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        queue.offer(new Dot(0L, 0));
        long cur = 0;
        while (k > 1) {
            queue.forEach(System.out::println);
            System.out.println("---------");
            Dot curMin = queue.poll();
            int pos = curMin.index;
            // 下一个最小序列有两种情况
            // 1. 将原来的序列中的最大值，更换为更大的值
            if (pos > 0 && pos + 1 <= nums.length) {
                long temp = curMin.value - (long) nums[pos - 1] + (long) nums[pos];
                queue.offer(new Dot(temp, pos + 1));
            }
            // 2. 直接加上后一个值
            if (pos + 1 <= nums.length) {
                long temp = curMin.value + (long) nums[pos];
                queue.offer(new Dot(temp, pos + 1));
            }
            k--;
        }

        return maxSum - queue.peek().value;
    }
}

class Dot {
    // 当前的最小值
    long value;
    // 下一个位置的索引
    int index;

    public Dot(long v, int i) {
        value = v;
        index = i;
    }

    public String toString() {
        return value + " # " + index;
    }
}