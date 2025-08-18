package me.while1cry.napcat4j.event.message;

import lombok.Getter;
import me.while1cry.napcat4j.entity.message.MessageArray;

@Getter
public class GroupMessageEvent extends MessageEvent {

    private final String groupId;
    private final String userId;

    public GroupMessageEvent(String groupId, String userId, String messageId, String rawMessage, MessageArray message) {
        super("message.group", message, rawMessage, messageId);
        this.groupId = groupId;
        this.userId = userId;
    }
}
