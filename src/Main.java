public class Main {
    public static void main(String[] args) {
//        int[] nums = {1,4,5,6};
//        System.out.println(binSearch(nums, 2));
//        System.out.println(0x80000000);
//        int x = foo();
//        System.out.println((2 * Math.pow(2, 30) % 512))
//        LongAdder
//        System.out.println(0x1f);

        System.out.println(handle("test.email+alex@leetcode.com"));
        System.out.println(handle("test.e.mail+bob.cathy@leetcode.com"));
        System.out.println(handle("testemail+david@lee.tcode.com"));
    }

    // 二分找到左边界
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

    private static String handle(String email) {
        char[] mail = email.toCharArray();

        // @符号的位置
        int pos = 0;
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < email.length() ;i++) {
            if (mail[i] >= 'a' && mail[i] <= 'z') {
                name.append(mail[i]);
            }else if (mail[i] == '.') {
                continue;
            }else if (mail[i] == '+') {
                while(mail[i] != '@') {
                    i++;
                }
                pos = i;
                break;
            }else if (mail[i] == '@') {
                pos = i;
                break;
            }
        }

        name.append('@');
        name.append(email.substring(pos + 1));

        return name.toString();
    }
}