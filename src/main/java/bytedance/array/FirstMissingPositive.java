package bytedance.array;

public class FirstMissingPositive {
    public static void main(String[] args) {
        FirstMissingPositive fs = new FirstMissingPositive();
        int []nums = {2,1,0};
        System.out.println(fs.firstMissingPositive(nums));
    }

    public int firstMissingPositive(int[] nums) {
        for (int i = 0;i < nums.length;i++) {
            while (nums[i] >= 0 && nums[i] < nums.length && nums[i] != nums[nums[i]]) {
                int temp = nums[i];
                nums[i] = nums[nums[i]];
                nums[temp] = temp;
            }
        }
        for (int i = 1; i < nums.length;i++) {
            if(i != nums[i]) {
                return i;
            }
        }
        return nums[0] == 0 ? nums.length : nums.length + 1;
    }
}
