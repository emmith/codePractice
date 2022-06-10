package designmodel.decorator;

public class BaseProfile implements ProfileComponent {

    private String name;

    public BaseProfile(String name) {
        this.name = name;
    }

    public void show() {
        System.out.printf("%s的形象\n", name);
    }
}
