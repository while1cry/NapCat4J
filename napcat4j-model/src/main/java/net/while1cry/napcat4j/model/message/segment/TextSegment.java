package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;

import java.util.Map;

@Getter
public final class TextSegment extends Segment {

    private final String text;

    public TextSegment(String text) {
        super("text");
        this.text = text;
    }

    @Override
    public Map<String, String> getData() {
        return Map.of("text", text);
    }
}
