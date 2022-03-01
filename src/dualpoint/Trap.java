package dualpoint;

public class Trap {
    public int trap(int[] height) {
        int len = height.length;
        if (len < 3) {
            return 0;
        }
        //存放当前位置的柱子左边最高的柱子
        int left = 0;
        //存放当前位置的柱子右边最高的柱子
        int[] right = new int[len];

        for (int i = len - 2; i > 0; i--) {
            right[i] = Math.max(height[i+1], right[i+1]);
        }
        int sum = 0;
        for (int i = 1;i < height.length - 1;i++) {
            left = Math.max(left, height[i-1]);
            if (height[i] < left && height[i] < right[i]){
                sum += Math.min(left, right[i]) - height[i];
            }
        }
        return sum;
    }
}
