package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileStreamTest {
    public static final int _MB = 1024;

    public static void main(String[] args) throws IOException {
        File src = new File("D:\\Users\\emmith\\Downloads\\Programs\\Feishu-win32_ia32-5.10.6-signed.exe");
        System.out.println("文件大小" + (src.length() / 1024 / 1024) + "M");
//        mmapTest(src);
        File dist1 = new File("resource/testfile/2.exe");
        File dist2 = new File("resource/testfile/3.exe");
        File dist3 = new File("resource/testfile/4.exe");
//
        copyFileByChannel(src, dist2);

        copyFile(src, dist1);
        fastCopy(src, dist3);
    }

    // 使用文件流复制文件，使用堆内缓存
    private static void copyFile(File src, File dist) throws IOException {
        System.out.println("-------------------使用堆内缓存-----------------------");
        long start = System.currentTimeMillis();

        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dist);
        byte[] buffer = new byte[20 * _MB];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        buffer = null;
        in.close();
        out.close();

        System.out.printf("%d ms\n", System.currentTimeMillis() - start);
    }

    // 使用管道复制
    private static void copyFileByChannel(File src, File dist) throws IOException {
        System.out.println("-------------------零拷贝-----------------------");

        long start = System.currentTimeMillis();

        FileChannel in = new FileInputStream(src).getChannel();
        FileChannel out = FileChannel.open(Paths.get(dist.toURI()), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        out.transferFrom(in, 0, in.size());

        in.close();
        out.close();

        System.out.printf("%d ms\n", System.currentTimeMillis() - start);
    }

    // 使用文件流复制文件，使用堆外缓存
    public static void fastCopy(File src, File dist) throws IOException {
        System.out.println("-------------------使用堆外缓存-----------------------");
        long start = System.currentTimeMillis();

        FileInputStream fin = new FileInputStream(src);
        FileChannel fcin = fin.getChannel();

        FileOutputStream fout = new FileOutputStream(dist);
        FileChannel fcout = fout.getChannel();

        ByteBuffer buf = ByteBuffer.allocateDirect(20 * _MB);

        while (true) {
            // 读到缓冲区
            int r = fcin.read(buf);

            if (r == -1) {
                break;
            }

            // 切换读写
            buf.flip();

            // 从缓冲区写入到文件
            fcout.write(buf);

            // 清空缓冲区
            buf.clear();
        }

        fcin.close();
        fcout.close();
        fin.close();
        fout.close();

        System.out.printf("%d ms\n", System.currentTimeMillis() - start);
    }

    private static void mmapTest(File file) throws IOException {
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
        long start = System.currentTimeMillis();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());

        System.out.println(System.currentTimeMillis() - start + "ms");
        map.clear();
        fileChannel.close();
    }
}
