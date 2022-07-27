package designmodel.observer;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        EventManager eventManager = new EventManager(FamousMan.RELEASE_TWEETS, FamousMan.RELEASE_VIDEO);
        FamousMan famousMan = new FamousMan(eventManager);

        Xiao xiao = new Xiao("魈");
        eventManager.subscribe(FamousMan.RELEASE_VIDEO, xiao);
        ZhongLi zhongLi = new ZhongLi("钟离");
        eventManager.subscribe(FamousMan.RELEASE_TWEETS, zhongLi);

        famousMan.releaseTweets();
        famousMan.releaseVideo();

    }
}
