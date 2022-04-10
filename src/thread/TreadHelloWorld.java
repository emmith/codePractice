package thread;

public class TreadHelloWorld {
    //main start会执行在 thread start和stop之前，至于main stop在前还是后
    //无法预料了，因为在thread启动后就是两个并发的线程了，线程的调度由操作系统来决定
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");

        Thread2 threadHello = new Thread2();
        threadHello.start();
        Thread.sleep(1000);
        threadHello.interrupt();

        System.out.println("main stop");
    }
}

class ThreadHello extends Thread {
    @Override
    public void run() {
        System.out.println("thread start");
        System.out.println("thread stop");
    }
}


class Thread2 extends Thread {
    int n = 0;

    public void run() {
        //收到中断指令（thread.interrupt()）就会停止执行
        while (!isInterrupted()) {
            n++;
            System.out.println("Thread2 " + n);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupted, but i can continue");
                break;
            }
        }
    }
}

//用volatile关键字，定义一个线程之间共享的变量，确保每一个线程可以读取到更新后的值
class HelloThread extends Thread {
    public volatile boolean running = true;

    public void run() {
        int n = 0;
        //如果running字段被修改为false就会停止
        while (running) {
            n++;
            System.out.println("HelloThread " + n);
        }
    }
}