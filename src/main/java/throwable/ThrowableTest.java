package throwable;

import java.io.IOException;

public class ThrowableTest {

    public static void main(String[] args) {
        IOException ioException = new IOException();
        MyThrowable.myThrow(ioException);
    }
}

class MyThrowable {
    public static <T extends Exception> void myThrow(Exception e) throws T {
        throw (T) e;
    }
}