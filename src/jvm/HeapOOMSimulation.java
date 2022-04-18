package jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOMSimulation {
    /**
     * 设置jvm参数
     * ”-Xms10m -Xmx10m -XX:+HeapDumpOnOutOfMemoryError
     * @param args
     */
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {

    }
}
