package mihoyo;


import java.util.concurrent.ThreadLocalRandom;

public class FiveStarStrategy implements LuckDrawStrategy{
    private static final FiveStarStrategy instance = new FiveStarStrategy();

    public static FiveStarStrategy getInstance() {
        return instance;
    }

    private FiveStarStrategy(){}
    @Override
    public String draw() {
        int i = ThreadLocalRandom.current().nextInt(Data.FIVE_STAR_CHARACTER.length);
        return "----五星---" + Data.FIVE_STAR_CHARACTER[i];
    }
}
