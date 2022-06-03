package DataStructure.bitmap;

public class BitMapInt {
    protected int[] data;

    private int capacity;

    public BitMapInt(int capacity) {
        this.capacity = capacity;
        data = new int[this.capacity];
    }

    public int set(int offset, int value) {
        // 校验偏移量是否超出范围
        if (offset >= (this.capacity << 5)) {
            return -1;
        }

        // 校验value是否为 0 或者 1
        if ((value & ~1) > 0) {
            return -1;
        }

        int byteIndex = offset >> 5;
        int bitIndex = offset & 0x1f;

        int bitCur = (data[byteIndex] & (1 << bitIndex)) >> bitIndex;
        int byteCur = data[byteIndex];

        // 不管对应位置是什么，先设置为零
        byteCur &= ~(1 << bitIndex);
        // 再进行或运算
        byteCur |= ((value & 0x1) << bitIndex);
        data[byteIndex] = byteCur;

        return bitCur;
    }

    public int get(int offset) {
        // 校验偏移量是否超出范围
        if (offset >= (this.capacity << 5)) {
            return -1;
        }

        int byteIndex = offset >> 5;
        int bitIndex = offset & 0x1f;

        return (data[byteIndex] >> bitIndex) & 0x1;
    }

    public int countBit() {
        int res = 0;
        for (int j = 0; j < capacity; j++) {
            long i = Integer.toUnsignedLong(data[j]);
            i = (i & 0x55555555) + ((i >>> 1) & 0x55555555);
            i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
            i = (i & 0x0f0f0f0f) + ((i >>> 4) & 0x0f0f0f0f);
            i = i + (i >>> 8);
            i = i + (i >>> 16);
            res += i & 0x3f;
        }
        return res;
    }

    private static void test1() {
        int capacity = 100000000 / 4;
        BitMapInt bitMapInt = new BitMapInt(capacity);
        for (int i = 0; i < 32 * capacity; i++) {
            bitMapInt.set(i, 1);
        }
        long start = System.currentTimeMillis();
        int n = bitMapInt.countBit();
        long end = System.currentTimeMillis() - start;
        System.out.println("位图法 " + end + "ms");
        System.out.println(n);
    }

    private static void test2() {
        BitMapInt bitMapInt = new BitMapInt(1);
        char[] num = Integer.toBinaryString(225).toCharArray();
        for (int i = 0; i < num.length ;i++) {
            bitMapInt.set(7 - i, num[i] - '0');
        }
        System.out.println(bitMapInt.data[0]);
    }

    public static void main(String[] args) {
        test2();
    }
}
