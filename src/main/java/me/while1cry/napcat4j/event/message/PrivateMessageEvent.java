package me.while1cry.napcat4j.event.message;

import lombok.Getter;
import me.while1cry.napcat4j.entity.message.MessageArray;

@Getter
public class PrivateMessageEvent extends MessageEvent {

    private final String subType;
    private final String userId;

    public PrivateMessageEvent(String subType, String userId, String messageId, String rawMessage, MessageArray message) {
        super("message.private", message, rawMessage, messageId);
        this.subType = subType;
        this.userId = userId;
    }
}
