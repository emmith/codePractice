package thread.threadpool;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ForkJoinTest {
    int start = 0;
    int end = 2000000000;

    static int[] num;

    static {
        num = new int[1000000000];
        for (int i = 0; i < num.length;i++) {
            num[i] = ThreadLocalRandom.current().nextInt();
        }
    }


    /**
     * 356ms
     * <p>
     * forkJoin基于分治的思想
     * 可以将大任务拆分为小任务
     * 充分利用多核的优势
     * <p>
     * Arrays.parallelSort就是利用的这个线程池
     */
    @Test
    public void forkJoinTest() {
        ForkJoinTask forkJoinTask = new SumTask(start, end);
        long res = (long) ForkJoinPool.commonPool().invoke(forkJoinTask);
        System.out.println(res);
    }

    //1785ms
    @Test
    public void test() {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

    //300ms
    @Test
    public void streamTest() {
        long sum = LongStream.rangeClosed(start, end).parallel().sum();
        System.out.println(sum);
    }

    @Test
    public void testMax() {
        StopWatch watch = DateUtil.createStopWatch();
        ForkJoinTask task = new MaxTask(0, num.length - 1, num);
        watch.start("1");
        int res1 = (int) ForkJoinPool.commonPool().invoke(task);
        watch.stop();
        watch.start("2");
        int res2 = 0;
        for (int i = 0; i < num.length ;i++) {
            res2 = Math.max(res2, num[i]);
        }
        watch.stop();
        watch.start("3");
        int res3 = Arrays.stream(num).parallel().max().getAsInt();
        watch.stop();
        System.out.println(watch.prettyPrint());
        System.out.println(res1 == res2);
    }
}

class SumTask extends RecursiveTask<Long> {
    static int thread = 524280;
    int start;
    int end;

    public SumTask(int _start, int _end) {
        start = _start;
        end = _end;
    }

    @Override
    protected Long compute() {
        if (end - start <= thread) {
            long sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }

        int mid = start + (end - start) / 2;
        SumTask sumTask1 = new SumTask(start, mid);
        SumTask sumTask2 = new SumTask(mid + 1, end);

        invokeAll(sumTask1, sumTask2);
        long result = sumTask1.join() + sumTask2.join();

        return result;
    }
}


class MaxTask extends RecursiveTask<Integer> {

    int low;
    int high;

    int[] data;

    private static final int threshold = 1 << 22;

    public MaxTask(int low, int high, int[] data) {
        this.low = low;
        this.high = high;
        this.data = data;
    }

    @Override
    protected Integer compute() {
        if (high - low <= threshold) {
            int max = data[low];
            for (int i = low; i <= high ;i++) {
                max = Math.max(max, data[i]);
            }
            return max;
        }
        int mid = (low + high) >> 1;

        MaxTask task1 = new MaxTask(low, mid, data);
        MaxTask task2 = new MaxTask(mid + 1, high, data);
        invokeAll(task1, task2);

        int res = Math.max(task1.join(), task2.join());
        return res;
    }
}