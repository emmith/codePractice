package utils;

import java.util.Arrays;

public class PrintUtil {
    public static <T> void printArray(T[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            printArray(arr[i]);
        }
    }

    public static <T> void printArray(T[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static  void printArray(boolean[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static  void printArray(boolean[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            printArray(arr[i]);
        }
    }

    public static  void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static  void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            printArray(arr[i]);
        }
    }
}
