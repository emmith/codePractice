package mihoyo;


public class LuckDraw {
    public Info info;

    public LuckDraw() {
        this.info = new Info();
    }

    // 抽奖
    public String draw() {
        // 先按普通概率来，五星优先
        double value = Math.random();
        return StrategyFactory.getStrategy(info, value).draw();
    }

    static class Info{
        // 总抽奖次数 < 90
        public int total;
        // 当前的抽奖次数 < 10
        public int counter;
        // 上一个五星是否为UP角色
        public boolean isLastFiveUP;
        // 上一个四星是否为UP角色
        public boolean isLastFourUP;
    }
}
