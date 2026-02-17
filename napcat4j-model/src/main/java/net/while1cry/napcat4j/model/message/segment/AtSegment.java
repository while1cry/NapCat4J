package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
public final class AtSegment extends Segment {

    @NotNull
    private final String qq;

    public AtSegment(@NotNull String qq) {
        super("at");
        this.qq = qq;
    }

    @Override
    public Map<String, String> getData() {
        return Map.of("qq", qq);
    }
}
