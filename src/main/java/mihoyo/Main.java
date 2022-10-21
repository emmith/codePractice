package mihoyo;

public class Main {

    public static void main(String[] args) {
        LuckDraw luckDraw = new LuckDraw();
        for (int i = 0; i < 1000 ;i++) {
            System.out.println(luckDraw.draw());
        }
    }
}
