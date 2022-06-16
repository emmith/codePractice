import java.util.Arrays;

public class MaxNum {
    public int maxNum(int target, int []nums) {
        //排序
        Arrays.sort(nums);
        //将数字转成字符串
        String str = Integer.toString(target);
        int min = nums[0];
        int max = nums[nums.length - 1];
        //存储结果
        StringBuilder res = new StringBuilder();
        for(int i = 0;i < str.length();i++) {
            //从最高位开始遍历数字的数位
            int cur = str.charAt(i) - '0';
            if (cur < min) { //当前数比数组中的最小值还小，返回比target少一位数的最大值
                res = new StringBuilder();
                fillNums(res, str.length() - 1, max);
                break;
            }
            if (cur > max) { //当前数比数组中的最大值还大，直接从当前位开始补全最大值
                fillNums(res, str.length() - i, max);
                break;
            }
            //找到不大于它的最接近它的数
            int closeNum = findCloseNums(cur, nums);
            if (closeNum == cur) { //相等，添加数字到首部，匹配下一位
                res.append(closeNum);
                continue;
            }
            if (closeNum < cur) { //小于当前位数
                //添加closeNums，closeNum以后都补最大值
                res.append(closeNum);
                fillNums(res, str.length() - i, max);
                break;
            }

        }
        return Integer.parseInt(res.toString());
    }

    //找到比它小的最接近它的数
    private int findCloseNums(int num, int []nums) {
        int i = 0;
        while (i+1 < nums.length && num >= nums[i+1]) {
            i++;
        }
        return nums[i];
    }

    //在res后面补n位num的数字
    private void fillNums(StringBuilder res, int n, int num) {
        while (n > 0) {
            res.append(num);
            n--;
        }
    }
}
