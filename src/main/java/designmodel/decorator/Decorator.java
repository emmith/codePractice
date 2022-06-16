package designmodel.decorator;

public class Decorator implements ProfileComponent {
    private ProfileComponent component;

    public void decorate(ProfileComponent component) {
        this.component = component;
    }

    @Override
    public void show() {
        if (component != null) {
            component.show();
        }
    }
}
