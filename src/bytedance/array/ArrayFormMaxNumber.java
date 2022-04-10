package bytedance.array;


import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrayFormMaxNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        //去除首尾中括号
        str = str.substring(1, str.length()-1);
        String []arr = str.split(",");
        System.out.println(formMaxNum(arr));
    }

    public static int formMaxNum(String[] arr) {
        Arrays.sort(arr, (s1, s2) -> {
            return (s2 + s1).compareTo(s1 + s2);
        });
        return Integer.valueOf(Arrays.stream(arr).collect(Collectors.joining()));
    }
}
