package ioc.example.test;

import ioc.annotation.Autowired;
import ioc.annotation.Component;

@Component
public class B{
    @Autowired
    private A a;

    @Autowired
    private C c;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    public void say() {
        System.out.println("I am B");
    }
}
