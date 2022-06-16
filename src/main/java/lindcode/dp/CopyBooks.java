package lindcode.dp;

public class CopyBooks {

    /**
     * @param pages: an array of integers
     * @param k:     An integer
     * @return: an integer
     */
    public static int copyBooks(int[] pages, int k) {
        // write your code here
        // 页的最大值
        int max = Integer.MIN_VALUE;
        //页数的总和
        int sum = 0;
        for (int i : pages) {
            max = Math.max(max, i);
            sum += i;
        }

        int left = max;
        int right = sum;

        while (left < right) {
            int mid = (left + right) / 2;

            //页数太小
            if (countCopyBooker(pages, mid) <= k) {
                right = mid;
            }else {
                left = mid + 1;
            }

        }

        return left;
    }

    public static int countCopyBooker(int[] pages, int num) {
        int count = 1;
        int cur = 0;
        for (int i : pages) {
            if (cur + i <= num) {
                cur += i;
            } else {
                cur = i;
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] page = {1,2,7};
        System.out.println(copyBooks(page, 5));
    }
}
