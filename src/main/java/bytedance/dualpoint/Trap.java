package bytedance.dualpoint;

public class Trap {
    public int trap(int[] height) {
        int len = height.length;
        int []right = new int[len];

        //将当前下标对应高度右边的最大值存储到right数组中
        for (int i = len - 2; i > 0;i--) {
            right[i] = Math.max(right[i+1], height[i+1]);
        }

        int res = 0;
        for (int left = 1,leftMax = 0;left < len - 1;left++) {
            leftMax = Math.max(leftMax, height[left-1]);
            int temp = Math.max(leftMax, right[left]) - height[left];
            res += temp > 0 ? temp: 0;
        }
        return res;
    }
}
