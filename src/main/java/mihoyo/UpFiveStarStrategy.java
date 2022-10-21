package mihoyo;

public class UpFiveStarStrategy implements LuckDrawStrategy{

    private static final UpFiveStarStrategy instance = new UpFiveStarStrategy();

    public static UpFiveStarStrategy getInstance() {
        return instance;
    }

    private UpFiveStarStrategy() {
    }

    @Override
    public String draw() {
        return "===UP五星===" + Data.UP_FIVE_STAR_CHARACTER;
    }
}
