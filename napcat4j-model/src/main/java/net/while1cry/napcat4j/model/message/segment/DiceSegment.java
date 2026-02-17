package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class DiceSegment extends Segment {

    @Nullable
    private final String result;

    public DiceSegment(@Nullable String result) {
        super("rps");
        this.result = result;
    }

    @Override
    public Map<String, String> getData() {
        return Collections.unmodifiableMap(new HashMap<>() {{put("result", result);}});
    }
}
