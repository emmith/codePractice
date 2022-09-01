package designmodel.template;

public class SyncCounter extends AbstractCounter{
    @Override
    public synchronized void inc() {
        super.inc();
    }

    @Override
    public synchronized void dec() {
        super.dec();
    }
}
