package dualpoint;

public class MaxArea {
    public int maxArea(int[] height) {
        int len = height.length;
        int left = 0, right = len - 1;
        int max_area = 0;

        while (left < right) {
            max_area = Math.max(max_area, Math.min(height[left] ,height[right]) * (right - left));
            if (height[left] < height[right]) {
                left++;
            }else {
                right--;
            }
        }
        return max_area;
    }

    //优化
    public int maxArea2(int[] height) {
        int len = height.length;
        int left = 0, right = len - 1;
        int max_area = 0;

        while (left < right) {
            int h = Math.min(height[left] ,height[right]);
            max_area = Math.max(max_area, h * (right - left));
            while (height[left] <= h && left < right) {
                left++;
            }
            while (height[right] <= h && left < right) {
                right--;
            }
        }
        return max_area;
    }
}
