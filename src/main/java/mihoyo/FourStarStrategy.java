package mihoyo;

import java.util.concurrent.ThreadLocalRandom;

public class FourStarStrategy implements LuckDrawStrategy{
    private static final FourStarStrategy instance = new FourStarStrategy();

    public static FourStarStrategy getInstance() {
        return instance;
    }

    private FourStarStrategy() {
    }

    @Override
    public String draw() {
        int i = ThreadLocalRandom.current().nextInt(Data.FOUR_STAR_CHARACTER.length);
        return "四星---" + Data.FOUR_STAR_CHARACTER[i];
    }
}
