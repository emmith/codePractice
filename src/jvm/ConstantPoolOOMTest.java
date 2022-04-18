package jvm;

import java.util.HashSet;
import java.util.Set;

public class ConstantPoolOOMTest {

    /**
     * //jdk 6及以前这样设置
     * VM Args -XX:PermSize=6M -XX:MaxPermSize=6M
     * //jdk 8 常量池移到了堆里 称为元数据 参数设置如下
     * -Xms6m -Xmx6m -XX:+HeapDumpOnOutOfMemoryError
     * @param args
     */
    public static void main(String[] args) {
        // 使用Set保持常量的引用，避免Full GC回收常量池
        Set<String> set = new HashSet<>();
        // 在short范围内足以让6M的堆 OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }

    }
}
