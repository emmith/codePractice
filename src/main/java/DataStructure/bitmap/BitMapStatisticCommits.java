package DataStructure.bitmap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 像github一样统计每日提交次数
 * 由于需要统计次数，所以我们采用一个字节来记录次数
 * 每天最多提交 255次
 * <p>
 */
public class BitMapStatisticCommits extends BitMapInt {

    public BitMapStatisticCommits(int capacity) {
        super(capacity);
    }

    @Override
    public int set(int offset, int value) {
        if ((value & ~255) > 0) {
            return -1;
        }

        char[] binVal = Integer.toBinaryString(value).toCharArray();
        int prefixZero = 8 - binVal.length;

        for (int i = 0; i < prefixZero; i++) {
            super.set(offset * 8 + i, 0);
        }

        for (int i = 0; i < binVal.length; i++) {
            super.set(offset * 8 + 7 - i - prefixZero, binVal[i] - '0');
        }
        return 0;
    }

    @Override
    public int get(int offset) {
        int ans = 0;
        for (int i = 7; i >= 0; i--) {
            ans |= super.get(offset * 8 + 7 - i) << (7 - i);
        }
        return ans;
    }

    public int count() {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            long temp = Integer.toUnsignedLong(data[i]);
            count += (temp & 0xFF);
            count += ((temp >> 8) & 0xFF);
            count += ((temp >> 16) & 0xFF);
            count += ((temp >> 24) & 0xFF);
        }
        return count;
    }

    private static void test1() {
        BitMapStatisticCommits bitMapStatisticCommits = new BitMapStatisticCommits(366);
        bitMapStatisticCommits.set(3, 255);
        bitMapStatisticCommits.set(2, 255);
        bitMapStatisticCommits.set(1, 255);
        bitMapStatisticCommits.set(0, 255);
        System.out.println(Integer.toUnsignedLong(bitMapStatisticCommits.data[0]) >> 24);
        System.out.println(Integer.toUnsignedLong(bitMapStatisticCommits.data[0]) >> 16);
        System.out.println(Integer.toUnsignedLong(bitMapStatisticCommits.data[0]) >> 8);
        System.out.println(Integer.toUnsignedLong(bitMapStatisticCommits.data[0]) >> 0);
    }

    private static void test2() {
        AtomicInteger count = new AtomicInteger();
        AtomicInteger res = new AtomicInteger();
        BitMapStatisticCommits bitMapStatisticCommits = new BitMapStatisticCommits(366);
        IntStream.rangeClosed(0, 365).forEach(i -> {
            int rm = ThreadLocalRandom.current().nextInt(255);
            res.addAndGet(rm);
            bitMapStatisticCommits.set(i, rm);
            if (bitMapStatisticCommits.get(i) == rm) {
                count.getAndIncrement();
            }
        });
        System.out.println(count.get());
        System.out.println(res.get() == bitMapStatisticCommits.count());
    }

    public static void main(String[] args) {
        test1();
    }
}
