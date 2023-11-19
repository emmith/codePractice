package thread.test.printabc;

public class PrintABC {
    public static final Object lock1 = new Object();

    public static volatile int state = 0;

    public static void main(String[] args) {
        PrintABC printABC = new PrintABC();
        printABC.print();
    }

    private void print() {
        Thread thread1 = new Thread(new Task(0, 1, 'A'));
        Thread thread2 = new Thread(new Task(1, 2, 'B'));
        Thread thread3 = new Thread(new Task(2, 0, 'C'));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    class Task implements Runnable {

        int preState;

        int nextState;

        int character;

        public Task(int pre, int next, char ch) {
            preState = pre;
            nextState = next;
            character = ch;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lock1) {
                    if (state == preState) {
                        System.out.println((char) character);
                        state = nextState;
                    }
                    try {
                        lock1.notifyAll();
                        lock1.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}