package ioc.example.test;

import ioc.annotation.Autowired;
import ioc.annotation.Component;

@Component
public class C {
    @Autowired
    private A a;

    @Autowired
    private B b;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public void say() {
        System.out.println("I am C");
    }
}
