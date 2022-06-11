package io;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 文件常用操作
 */
public class FileTest {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("./resource");
        deepListAllFiles(file);
    }

    // 递归列出目录下所有文件名
    private static void deepListAllFiles(File file) {
        deepListAllFiles(file, "");
    }

    private static void deepListAllFiles(File file, String tab) {
        if (file == null || !file.exists()) {
            return;
        }

        System.out.printf("%s%s\n", tab, file.getName());
        if (file.isFile()) {
            return;
        }

        for (File file1 : file.listFiles()) {
            deepListAllFiles(file1, tab + "--");
        }
    }

    // 只打印目录下的文件名，不会打印子孙文件
    private static void listAllFiles(File file) {
        if (file == null || !file.exists()) {
            return;
        }

        for (File file1 : file.listFiles()) {
            System.out.printf("%s\n", file1.getName());
        }
    }
}
