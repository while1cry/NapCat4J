package net.while1cry.napcat4j.model.message.segment;

import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class PokeSegment extends Segment {

  @NotNull private final String type;
  @NotNull private final String id;

  public PokeSegment(@NotNull String type, @NotNull String id) {
    super("poke");
    this.type = type;
    this.id = id;
  }

  @Override
  public Map<String, String> getData() {
    return Map.of(
        "type", type,
        "id", id);
  }
}
