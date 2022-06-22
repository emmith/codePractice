package designmodel.observer;

public class FamousMan {
    public static final String RELEASE_VIDEO = "RV";
    public static final String RELEASE_TWEETS = "RT";

    public static final String RELEASE_VIDEO_INFO = "FamousMan 发布了小视频";
    public static final String RELEASE_TWEETS_INFO = "FamousMan 发布了新的推文";

    private EventManager eventManager;

    public FamousMan(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void releaseVideo() {
        eventManager.notify(RELEASE_VIDEO, RELEASE_VIDEO_INFO);
    }

    public void releaseTweets() {
        eventManager.notify(RELEASE_TWEETS, RELEASE_TWEETS_INFO);
    }
}
