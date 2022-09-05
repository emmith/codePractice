package ioc.example.test;

import ioc.annotation.Autowired;
import ioc.annotation.Component;

@Component
public class A {

    @Autowired
    private B b;

    @Autowired
    private C c;

    public void say() {
        System.out.println("I am A");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }
}
