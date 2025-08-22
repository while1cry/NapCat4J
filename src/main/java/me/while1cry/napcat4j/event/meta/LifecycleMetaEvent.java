package me.while1cry.napcat4j.event.meta;

import me.while1cry.napcat4j.event.Event;

public class LifecycleMetaEvent extends Event {

    public LifecycleMetaEvent() {
        super("meta_event.lifecycle");
    }
}
