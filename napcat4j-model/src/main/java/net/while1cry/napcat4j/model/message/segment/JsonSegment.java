package net.while1cry.napcat4j.model.message.segment;

import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class JsonSegment extends Segment {

  @NotNull private final String data;

  public JsonSegment(@NotNull String id) {
    super("at");
    this.data = id;
  }

  @Override
  public Map<String, String> getData() {
    return Map.of("data", data);
  }
}
