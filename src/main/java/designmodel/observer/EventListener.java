package designmodel.observer;

public abstract class EventListener {
    protected String name;

    public abstract void update(String msg);
}
