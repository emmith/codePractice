package thread.test.printabc;

import java.util.concurrent.Semaphore;

public class PrintABCSemaphore {
    static Semaphore a = new Semaphore(1);
    static Semaphore b = new Semaphore(0);
    static Semaphore c = new Semaphore(0);

    public static void main(String[] args) {
        Thread threadA = new Thread(new Task(a, b, 'A'));
        Thread threadB = new Thread(new Task(b, c, 'B'));
        Thread threadC = new Thread(new Task(c, a, 'C'));

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

class Task implements Runnable {
    Semaphore pre;
    Semaphore next;
    char character;

    public Task(Semaphore pre, Semaphore next, char character) {
        this.pre = pre;
        this.next = next;
        this.character = character;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                pre.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("%c\n", character);
            next.release();
        }
    }
}