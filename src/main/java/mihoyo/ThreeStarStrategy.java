package mihoyo;

import java.util.concurrent.ThreadLocalRandom;

public class ThreeStarStrategy implements LuckDrawStrategy {

    private ThreeStarStrategy() {
    }

    private static final ThreeStarStrategy instance = new ThreeStarStrategy();

    public static ThreeStarStrategy getInstance() {
        return instance;
    }

    @Override
    public String draw() {
        int i = ThreadLocalRandom.current().nextInt(Data.THREE_STAR_WEAPON.length);
        return Data.THREE_STAR_WEAPON[i];
    }
}
