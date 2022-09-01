package designmodel.template;

public abstract class AbstractCounter {
    private int n = 0;

    protected void inc() {
        n++;
    }

    protected void dec() {
        n--;
    }

    public void printN(){
        System.out.println(n);
    }
}
