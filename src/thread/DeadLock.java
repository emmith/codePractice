package thread;


public class DeadLock {
    public final Object cloth = new Object();
    public final Object pains = new Object();


    public void test() {
        Thread thread1 = new Thread(() -> {
            try {
                apply1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;
        Thread thread2 = new Thread(() -> {
            try {
                apply2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }) ;
        thread1.start();
        thread2.start();
    }

    public void apply1() throws InterruptedException {
        Thread thread = Thread.currentThread();
        synchronized (cloth) {
            System.out.println(thread + "申请到了衣服，即将申请裤子");
            Thread.sleep(1000);
            System.out.println(thread.getState());//runnable
            synchronized (pains) {
                System.out.println(thread +"申请到了裤子，资源获取完毕");
            }
        }
    }

    public void apply2() throws InterruptedException {
        Thread thread = Thread.currentThread();
        synchronized (pains) {
            System.out.println(thread +"申请到了裤子，即将申请衣服");
            Thread.sleep(1000);
            System.out.println(thread.getState()); //runnable
            synchronized (cloth) {
                System.out.println(thread +"申请到了裤子，资源获取完毕");
            }
        }
    }

    public static void main(String[] args) {

        DeadLock test = new DeadLock();
        test.test();
    }
}

class enterNable {
    int count = 0;
    public synchronized void add(int n) {
            if (n < 0) {
                dec(-n);
            }else {
                count += n;
            }
    }

    public synchronized void dec(int n) {
        count -= n;
    }
}