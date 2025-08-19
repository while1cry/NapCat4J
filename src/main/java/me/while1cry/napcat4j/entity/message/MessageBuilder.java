package me.while1cry.napcat4j.entity.message;

import me.while1cry.napcat4j.entity.message.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MessageBuilder {

    private final List<Message> messages = new ArrayList<>();

    private MessageBuilder() {

    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static MessageBuilder builder(List<Message> messages) {
        MessageBuilder builder = new MessageBuilder();
        builder.messages.addAll(messages);
        return builder;
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

    public MessageBuilder replace(String placeholder, Message replacement) {
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if ("text".equals(message.getType()) && message.getData() instanceof TextData textData) {
                String text = textData.getText();
                String quotedPlaceholder = Pattern.quote(placeholder);
                if (text.equals(placeholder)) {
                    messages.set(i, replacement);
                } else if (text.contains(placeholder)) {
                    String[] parts = text.split(quotedPlaceholder, 2);
                    if (parts.length < 2) continue;
                    String beforeText = parts[0];
                    String afterText = parts[1];
                    messages.remove(i);
                    if (! afterText.isEmpty()) {
                        messages.add(i, new Message("text", new TextData(afterText)));
                        i ++;
                    }
                    messages.add(i, replacement);
                    if (! beforeText.isEmpty()) {
                        messages.add(i, new Message("text", new TextData(beforeText)));
                        i ++;
                    }
                }
            }
        }
        return this;
    }

    public MessageBuilder append(Message message) {
        messages.add(message);
        return this;
    }

    public MessageArray build() {
        return new MessageArray(messages.toArray(new Message[0]));
    }
}
