package me.while1cry.napcat4j.event.meta;

import lombok.Getter;
import me.while1cry.napcat4j.event.Event;

@Getter
public class ConnectMetaEvent extends Event {

    private final long time;
    private final String selfId;

    public ConnectMetaEvent(long time, String selfId) {
        super("meta_event.lifecycle.connect");
        this.time = time;
        this.selfId = selfId;
    }
}
