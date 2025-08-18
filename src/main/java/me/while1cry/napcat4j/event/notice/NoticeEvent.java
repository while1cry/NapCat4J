package me.while1cry.napcat4j.event.notice;

import me.while1cry.napcat4j.event.Event;

public abstract class NoticeEvent extends Event {
    public NoticeEvent(String eventName) {
        super(eventName);
    }
}
