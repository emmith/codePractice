package DataStructure;


public class BitMap {
    // 查表法，统计0到255中1的个数
    private final static byte[] bitsinbyte = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};

    public byte[] data;

    private int capacity;

    public BitMap(int capacity) {
        this.capacity = capacity;
        data = new byte[this.capacity];
    }

    public int set(int offset, int value) {
        // 校验偏移量是否超出范围
        if (offset >= (this.capacity << 3)) {
            return -1;
        }

        // 校验value是否为 0 或者 1
        if ((value & ~1) > 0) {
            return -1;
        }

        int byteIndex = offset >> 3;
        int bitIndex = offset & 0x7;

        int bitCur = (data[byteIndex] & (1 << bitIndex)) >> bitIndex;
        byte byteCur = data[byteIndex];

        // 不管对应位置是什么，先设置为零
        byteCur &= ~(1 << bitIndex);
        // 再进行或运算
        byteCur |= ((value & 0x1) << bitIndex);
        data[byteIndex] = byteCur;

        return bitCur;
    }

    public int get(int offset) {
        // 校验偏移量是否超出范围
        if (offset >= (this.capacity << 3)) {
            return -1;
        }

        int byteIndex = offset >> 3;
        int bitIndex = offset & 0x7;

        return (data[byteIndex] >> bitIndex) & 0x1;
    }

    public int countBit1() {
        int res = 0;
        for (int i = 0; i < capacity ;i++) {
            res += bitsinbyte[Byte.toUnsignedInt(data[i])];
        }
        return res;
    }

    public static void main(String[] args) {
        int capacity = 100000000;
        BitMap bitMap = new BitMap(capacity);
        for (int i = 0; i < capacity * 8 ;i++) {
            bitMap.set(i, 1);
        }

        long start = System.currentTimeMillis();
        int  m = bitMap.countBit1();
        long mid = System.currentTimeMillis();

        System.out.println("查表法 " + (mid - start) + "ms");
        System.out.println(m);
    }
}
