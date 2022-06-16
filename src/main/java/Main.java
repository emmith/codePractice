
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<Integer, Integer> map = new TreeMap<>();
        int []nums  = {9,8,7,6,2,231,1,1,2,2,7,6,9};
        for (int i = 0;i < nums.length ;i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        for (int i : map.keySet()) {
            System.out.println(i);
        }
    }
}