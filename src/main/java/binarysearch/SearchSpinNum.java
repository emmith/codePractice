package binarysearch;

public class SearchSpinNum {
    //找到旋转数组的拐点
    public static int search(int []arr) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid-1] < arr[mid]) {
                if(arr[mid] < arr[left] && arr[mid] > arr[right]) {
                    left = mid + 1;
                }else {
                    right = mid;
                }
            }else {
                return mid;
            }
        }
        return 0;
    }

    //在旋转数组中找到k
    public static int search(int []arr, int k) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid-1] < arr[mid]) {
                if(arr[mid] < arr[left] && arr[mid] > arr[right]) {
                    left = mid + 1;
                }else {
                    right = mid;
                }
            }else {
                return mid;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[] {9,10,1,2,3,4,5,6,7,8}));
    }
}
