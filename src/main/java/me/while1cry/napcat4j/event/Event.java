package me.while1cry.napcat4j.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Event {
    private final String eventName;
}
