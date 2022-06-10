package designmodel.decorator;

public class DressingTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println("--------------------------");
        BaseProfile xiao = new BaseProfile("魈");
        Jeans jeans = new Jeans();
        TShirt tShirt = new TShirt();
        Shirt shirt = new Shirt();

        tShirt.decorate(xiao);
        shirt.decorate(tShirt);
        jeans.decorate(shirt);
        jeans.show();
    }

    private static void test2() {
        System.out.println("--------------------------");
        BaseProfile ying = new BaseProfile("莹");

        TShirt tShirt = new TShirt();
        Skirt skirt = new Skirt();

        tShirt.decorate(ying);
        skirt.decorate(tShirt);

        skirt.show();
    }

    private static void test3() {
        System.out.println("--------------------------");
        BaseProfile keqing = new BaseProfile("刻晴");

        TShirt tShirt = new TShirt();
        Skirt skirt = new Skirt();
        Shirt shirt = new Shirt();
        CasualPants casualPants = new CasualPants();

        tShirt.decorate(keqing);
        shirt.decorate(tShirt);
        skirt.decorate(shirt);
        casualPants.decorate(skirt);

        casualPants.show();
    }
}
