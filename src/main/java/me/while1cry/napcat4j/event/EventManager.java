package me.while1cry.napcat4j.event;

import me.while1cry.napcat4j.subscriber.DefaultSubscriber;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private final EventBus eventBus = new EventBus();
    private final List<Object> listeners = new ArrayList<>();

    public EventManager(Logger logger) {
        eventBus.register(new DefaultSubscriber(logger));
    }

    public void registerListener(Object listener) {
        eventBus.register(listener);
        listeners.add(listener);
    }

    public void unregisterListener(Object listener) {
        eventBus.unregister(listener);
        listeners.remove(listener);
    }

    public void unregisterAll() {
        listeners.forEach(eventBus::unregister);
        listeners.clear();
    }

    public void callEvent(Object event) {
        eventBus.post(event);
    }
}
