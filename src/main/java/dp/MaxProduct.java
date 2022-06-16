package dp;


public class MaxProduct {
    public int maxProduct(int[] nums) {
        int len = nums.length;

        int max = Integer.MIN_VALUE;
        for (int i = 0, imin = 1, imax = 1; i < len; i++) {
            if (nums[i] < 0) {
                int temp = imin;
                imin = imax;
                imax = temp;
            }
            imax = Math.max(imax * nums[i], nums[i]);
            imin = Math.min(imin * nums[i], nums[i]);
            max = Math.max(max, imax);
        }
        return max;
    }

    public int maxProduct2(int[] nums) {
        int left = 1;
        int right = 1;
        int max = Integer.MIN_VALUE;
        int len = nums.length;

        for (int i = 0; i < len; i++) {
            left *= nums[i];
            right *= nums[len - i - 1];

            max = Math.max(max, left);
            max = Math.max(max, right);
            if (nums[i] == 0){
                left = 0;
            }
            if (nums[len - i - 1] == 0){
                right = 0;
            }
        }
        return max;
    }
}
