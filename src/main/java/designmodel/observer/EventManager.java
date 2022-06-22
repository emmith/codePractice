package designmodel.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private Map<String, List<EventListener>> events;

    public EventManager(String ...operations) {
        events = new HashMap<>();
        for (String operation : operations) {
            events.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> list = events.get(eventType);
        list.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> list = events.get(eventType);
        list.remove(listener);
    }

    public void notify(String eventType, String msg) {
        List<EventListener> list = events.get(eventType);
        for (EventListener el: list) {
            el.update(msg);
        }
    }
}
