package throwable;

public class ThrowableTest {

    public static void main(String[] args) {
        NullPointerException nullPointerException = new NullPointerException();
        MyThrowable.myThrow(nullPointerException);
    }
}

class MyThrowable {
    public static <T extends Exception> void myThrow(Exception e) throws T {
        throw (T)e;
    }
}