import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        int[] nums = {1,4,5,6};
//        System.out.println(binSearch(nums, 2));
//        System.out.println(0x80000000);
//        int x = foo();
//        System.out.println((2 * Math.pow(2, 30) % 512))
//        LongAdder
        ArrayList a = new ArrayList<>();
        a.clone();
    }

    private static int binSearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = ((right - left) >> 1) + left;

            if (nums[mid] < target) {
                left = mid + 1;
            } else
                right = mid;
        }

        System.out.println("l  " + left);
        System.out.println("r  " + right);
        return left;
    }

    private static int bitCount(int num) {
        int count = 0;
        while (num != 0) {
            count += (num % 2);
            num = num >> 1;
        }
        return count;
    }


    public static int foo() {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }
}