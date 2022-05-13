package thread.threadpool;

import org.testng.annotations.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinTest {
    int start = 0;
    int end = 2000000000;


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
    void forkJoinTest() {
        ForkJoinTask forkJoinTask = new SumTask(start, end);
        long res = (long) ForkJoinPool.commonPool().invoke(forkJoinTask);
        System.out.println(res);
    }

    //1785ms
    @Test
    void test() {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

    //300ms
    @Test
    void streamTest() {
        long sum = LongStream.rangeClosed(start, end).parallel().sum();
        System.out.println(sum);
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
