package thread.dispatcher;

import utils.ThreadUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


// 蚂蚁面试题
// 实现一个高性能调度器
// 任务之间存在依赖关系，需要先完成前置任务后续任务才能开始
// 拓扑排序先完成没有前置任务的节点，通过入度判断下一个任务能否开始
public class Dispatcher {
    /**
     * 维度：n * 2
     * 任务之间的依赖关系
     * relations[0] 要等 relations[1]执行完成才能执行
     * {{0,1}, {2, 3}} 表示 任务0要等任务1执行完成才能开始，任务3要等任务2执行完成才能开始
     */
    private int[][] relations;
    // 总任务数量
    private int taskNumbers;

    private long[] sleepTime;
    // 当前剩余任务数量
    private AtomicInteger state;
    // 阻塞队列，用于存储当前可执行的任务
    private LinkedBlockingQueue<Integer> queue;
    // 信号量，用于线程通讯
    private Semaphore semaphore;

    public Dispatcher(int[][] relations, int taskNumbers, long[] sleepTime) {
        this.relations = relations;
        this.taskNumbers = taskNumbers;
        this.sleepTime = sleepTime;
    }

    private void init() {
        this.state = new AtomicInteger(taskNumbers);
        this.queue = new LinkedBlockingQueue<>();
        this.semaphore = new Semaphore(0);
    }

    // 主线程，用于将入度为零的任务交给执行线程执行
    public void start() throws ExecutionException, InterruptedException {
        init();
        List<Integer>[] graph = createGraph(taskNumbers, relations);
        // 通过入度判断当前任务是否具备执行条件
        LongAdder[] degree = getInDegree(taskNumbers, relations);

        for (int i = 0; i < degree.length; i++) {
            if (degree[i].intValue() == 0) queue.add(i);
        }

        while (true) {
            if (queue.size() == 0 && state.get() > 0) {
                System.out.println("睡觉......");
                semaphore.tryAcquire(100, TimeUnit.MILLISECONDS);
                System.out.println("被唤醒.......");
            }
            int size = queue.size();
            while (size > 0) {
                int curTaskId = queue.poll();
                CompletableFuture.runAsync(new Task(curTaskId, sleepTime[curTaskId])).whenComplete((res, th) -> {
                    state.decrementAndGet();
                    for (int node : graph[curTaskId]) {
                        degree[node].decrement();
                        if (degree[node].intValue() == 0) {
                            queue.add(node);
                            semaphore.release();
                        }
                    }
                });
                size--;
            }
            if (state.get() == 0) break;
        }
    }

    private LongAdder[] getInDegree(int n, int[][] lessons) {
        LongAdder[] degree = new LongAdder[n];
        for (int i = 0; i < n; i++) {
            degree[i] = new LongAdder();
        }
        for (int[] lesson : lessons) {
            degree[lesson[0]].add(1);
        }
        return degree;
    }

    private List<Integer>[] createGraph(int n, int[][] lessons) {
        List<Integer>[] graph = new List[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] lesson : lessons) {
            graph[lesson[1]].add(lesson[0]);
        }

        return graph;
    }

    static class Task implements Runnable {
        private int id;
        private long sleepTime;

        public Task(int id, long time) {
            this.id = id;
            this.sleepTime = time;
        }

        @Override
        public void run() {
            System.out.printf("任务 %d 正在执行......\n", this.id);
            ThreadUtil.millsSleep(sleepTime);
            System.out.printf("任务 %d 执行完毕......\n", this.id);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int n = 6;
        int[][] relations = {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {5, 1}};
        long[] sleepTime = new long[n];
        Random rm = new Random(481274);
        long sumTime = 0;
        for (int i = 0; i < 6; i++) {
            sleepTime[i] = rm.nextInt(1000);
            sumTime += sleepTime[i];
        }
        System.out.println(Arrays.toString(sleepTime));
        System.out.println("单线程执行用时" + sumTime + "ms");
        Dispatcher dispatcher = new Dispatcher(relations, n, sleepTime);
        long cur = System.currentTimeMillis();
        dispatcher.start();
        System.out.println("使用调度器执行用时" + (System.currentTimeMillis() - cur) + "ms");
    }
}
