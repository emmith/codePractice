package thread.cas;

import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;


/**
 * 测试AtomicInteger和LongAdder的性能差距
 *
 *
 */
public class LongAdderTest {
    public static final LongAdder longAdder = new LongAdder();

    public static final AtomicInteger atomicInteger = new AtomicInteger();

    public static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static final int LOOP_COUNT = 10000000;


    @Test
    void testLongAdder() {
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, THREAD_COUNT).parallel().forEach(i -> {
            for (int j = 0; j < LOOP_COUNT; j++) {
                longAdder.increment();
            }
        });
        System.out.println("LongAdder: " + (System.currentTimeMillis() - start));
        System.out.println(longAdder.longValue());
    }


    @Test
    void testAtomicInteger() {
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, THREAD_COUNT).parallel().forEach(i -> {
            for (int j = 0; j < LOOP_COUNT; j++) {
                atomicInteger.incrementAndGet();
            }
        });
        System.out.println("atomicAdder: " + (System.currentTimeMillis() - start));
        System.out.println(atomicInteger.get());
    }
}
