package thread.threadpool;


import utils.ThreadUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    public static void main(String[] args) throws Exception {
        ThreadPoolExecutor es = new ThreadPoolExecutor(2, 2, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(500));
        String[] sqls = {"Mysql", "SqlLite", "PgSql", "TiDB", "ElasticSearch"};
        for (String sql : sqls) {
            es.submit(new Task(sql));
        }
        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);
    }
}

class SqlConnection implements AutoCloseable {
    private static final ThreadLocal<String> sqlInfo = new ThreadLocal<>();

    public SqlConnection(String sqlConnection) {
        /*我们去看ThreadLocal的源代码
            可以发现首先是获取当前线程的Id
            通过当前线程的Id拿到当前线程的ThreadLocalMap
            然后再存进去

            所以实际上我们每一个线程都自己带了一个ThreadLocal
            因此他能实现各个线程ThreadLocal的隔离
            保证对应线程中的方法能获取到对应的变量

            我们可以认为存储的变量是   线程内全局变量
         *
         */
        sqlInfo.set(sqlConnection);
        System.out.printf("[%s] Init sql connection.......[%s] \n", Thread.currentThread().getName(), SqlConnection.getCurrentSql());
    }

    public static String getCurrentSql() {
        return sqlInfo.get();
    }

    /*
     * 因为我们用的是线程池
     * 如果我们不关闭
     * 当本任务完成以后
     * 别的任务会继续复用这个线程
     * 就可以读取到上一个任务存储的变量
     *
     * 造成内存泄漏
     *
     * 为了保证安全性和一些奇怪的bug
     * 我们使用完后必须remove
     */
    @Override
    public void close() {
        System.out.printf("[%s] closing sql connection..............[%s] \n", Thread.currentThread().getName(), SqlConnection.getCurrentSql());
        sqlInfo.remove();
    }
}

class Task implements Runnable {

    private final String sql;

    Task(String sql) {
        this.sql = sql;
    }

    @Override
    public void run() {
        try (SqlConnection sqlConnection = new SqlConnection(this.sql)) {
            /*
                由于这三个普通方法，执行在同一个线程里
                他们读取到本线程存储到ThreadLocal中的数据是一致的

                这样我们可以避免需要给每个方法存入参数的问题
             */
            Common.startWork();
            Common.working();
            Common.closeWork();
        }
    }
}

/*
    使用threadLocal
    避免了需要给下面的三个方法传入 对应的数据库连接参数
 */
class Common {
    public static void startWork() {
        ThreadUtil.randomSleep();
        System.out.printf("[%s] start work.............. use [%s]\n", Thread.currentThread().getName(), SqlConnection.getCurrentSql());
    }

    public static void working() {
        ThreadUtil.randomSleep();
        System.out.printf("[%s] is working.............. use [%s]\n", Thread.currentThread().getName(), SqlConnection.getCurrentSql());
    }

    public static void closeWork() {
        ThreadUtil.randomSleep();
        System.out.printf("[%s] close work.............. use [%s]\n", Thread.currentThread().getName(), SqlConnection.getCurrentSql());
    }
}