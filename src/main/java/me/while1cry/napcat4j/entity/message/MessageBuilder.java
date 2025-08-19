package me.while1cry.napcat4j.entity.message;

import me.while1cry.napcat4j.entity.message.data.*;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {

    private final List<Message> messages = new ArrayList<>();

    private MessageBuilder() {

    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder text(String text) {
        messages.add(new Message("text", new TextData(text)));
        return this;
    }

    public MessageBuilder at(String userId) {
        messages.add(new Message("at", AtData.at(userId)));
        return this;
    }

    public MessageBuilder atAll() {
        messages.add(new Message("at", AtData.atAll()));
        return this;
    }

    public MessageBuilder face(String id) {
        messages.add(new Message("face", new FaceData(id)));
        return this;
    }

    public MessageBuilder image(String url) {
        SendImageData image = new SendImageData();
        image.setFile(url);
        messages.add(new Message("image", image));
        return this;
    }

    public MessageBuilder image(String name, String summary, String url, String subType) {
        SendImageData image = new SendImageData();
        image.setName(name);
        image.setSummary(summary);
        image.setFile(url);
        image.setSub_type(subType);
        messages.add(new Message("image", image));
        return this;
    }

    public MessageBuilder reply(String messageId) {
        messages.add(new Message("reply", new ReplyData(messageId)));
        return this;
    }

    public MessageBuilder newline() {
        messages.add(new Message("text", new TextData("\n")));
        return this;
    }

    public MessageArray build() {
        return new MessageArray(messages.toArray(new Message[0]));
    }
}
