package me.while1cry.napcat4j.event.message;

import lombok.Getter;
import me.while1cry.napcat4j.entity.message.MessageArray;
import me.while1cry.napcat4j.event.Event;

@Getter
public abstract class MessageEvent extends Event {

    private final MessageArray message;
    private final String rawMessage;
    private final String id;

    public MessageEvent(String eventName, MessageArray message, String rawMessage, String messageId) {
        super(eventName);
        this.message = message;
        this.rawMessage = rawMessage;
        this.id = messageId;
    }
}
