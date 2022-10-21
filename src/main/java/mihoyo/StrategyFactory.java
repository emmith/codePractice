package mihoyo;

import mihoyo.LuckDraw.Info;

public class StrategyFactory {
    public static LuckDrawStrategy getStrategy(Info info, double radix) {
        boolean upIncrease = false;
        if (info.total >= 73 && radix < 0.5) upIncrease = true;
        if (upIncrease || radix < Data.FIVE_STAR_RADIX || info.total == 89) {
            info.total = 0;
            info.counter++;
            // 上一次是up角色，这一次则随机
            if (info.isLastFiveUP) {
                if (Math.random() < 0.5) {
                    // 歪了
                    info.isLastFiveUP = false;
                    return FiveStarStrategy.getInstance();
                }else {
                    return UpFiveStarStrategy.getInstance();
                }
            }else {
                info.isLastFiveUP = true;
                return UpFiveStarStrategy.getInstance();
            }
        }else if (radix < Data.FOUR_STAR_RADIX || info.counter >= 9) {
            info.total++;
            info.counter = 0;
            if (info.isLastFourUP) {
                if (Math.random() < 0.5) {
                    info.isLastFourUP = false;
                    return FourStarStrategy.getInstance();
                }else {
                    return UPFourStarStrategy.getInstance();
                }
            }else {
                info.isLastFourUP = true;
                return UPFourStarStrategy.getInstance();
            }
        }
        info.total++;
        info.counter++;
        return ThreeStarStrategy.getInstance();
    }
}
