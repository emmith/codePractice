package designmodel.observer;

public class Main {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager(FamousMan.RELEASE_TWEETS, FamousMan.RELEASE_VIDEO);
        FamousMan famousMan = new FamousMan(eventManager);

        Xiao xiao = new Xiao();
        eventManager.subscribe(FamousMan.RELEASE_VIDEO, xiao);
        ZhongLi zhongLi = new ZhongLi();
        eventManager.subscribe(FamousMan.RELEASE_TWEETS, zhongLi);

        famousMan.releaseTweets();
        famousMan.releaseVideo();
    }
}
