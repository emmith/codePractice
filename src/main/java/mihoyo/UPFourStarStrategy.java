package mihoyo;

import java.util.concurrent.ThreadLocalRandom;

public class UPFourStarStrategy implements LuckDrawStrategy {
    private static final UPFourStarStrategy instance = new UPFourStarStrategy();

    public static UPFourStarStrategy getInstance() {
        return instance;
    }

    private UPFourStarStrategy() {
    }

    @Override
    public String draw() {
        int i = ThreadLocalRandom.current().nextInt(Data.UP_FOUR_STAR_CHARACTER.length);
        return "UP四星---" + Data.UP_FOUR_STAR_CHARACTER[i];
    }
}
