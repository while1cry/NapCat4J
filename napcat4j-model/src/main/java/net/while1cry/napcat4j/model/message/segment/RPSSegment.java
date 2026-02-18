package net.while1cry.napcat4j.model.message.segment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public final class RPSSegment extends Segment {

  @Nullable private final String result;

  public RPSSegment(@Nullable String result) {
    super("rps");
    this.result = result;
  }

  @Override
  public Map<String, String> getData() {
    return Collections.unmodifiableMap(
        new HashMap<>() {
          {
            put("result", result);
          }
        });
  }
}
