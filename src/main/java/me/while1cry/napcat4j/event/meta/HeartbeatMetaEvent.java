package me.while1cry.napcat4j.event.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.while1cry.napcat4j.event.Event;

@Getter
public class HeartbeatMetaEvent extends Event {

    private final long time;
    private final String selfId;
    private final Status status;
    private final long interval;

    public HeartbeatMetaEvent(long time, String selfId, Status status, long interval) {
        super("meta_event.heartbeat");
        this.time = time;
        this.selfId = selfId;
        this.status = status;
        this.interval = interval;
    }

    @Getter
    @AllArgsConstructor
    public static final class Status {
        private final boolean online;
        private final boolean good;
    }
}
