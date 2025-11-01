package me.while1cry.napcat4j.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
    private final Sinks.Many<Object> sink =
            Sinks.many().multicast().onBackpressureBuffer();
    private final Logger logger = LoggerFactory.getLogger("EventManager");

    private final Map<Class<?>, List<ListenerMethod>> listeners = new ConcurrentHashMap<>();

    public EventManager() {
        sink.asFlux()
                .publishOn(Schedulers.boundedElastic())
                .subscribe(this::dispatchEvent);
    }

    public void register(Object listener) {
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class) && method.getParameterCount() == 1) {
                Class<?> eventType = method.getParameterTypes()[0];
                method.setAccessible(true);
                listeners
                        .computeIfAbsent(eventType, k -> new ArrayList<>())
                        .add(new ListenerMethod(listener, method));
            }
        }
    }

    public void unregister(Object listener) {
        listeners.values().forEach(list ->
                list.removeIf(lm -> lm.target.equals(listener)));
    }

    public void post(Object event) {
        sink.tryEmitNext(event);
    }

    private void dispatchEvent(Object event) {
        List<ListenerMethod> list = listeners.get(event.getClass());
        if (list != null) {
            for (ListenerMethod lm : list) {
                try {
                    lm.method.invoke(lm.target, event);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private static class ListenerMethod {
        final Object target;
        final Method method;
        ListenerMethod(Object t, Method m) {
            this.target = t;
            this.method = m;
        }
    }
}
