package net.while1cry.napcat4j.model.message.segment;

import java.util.Map;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ReplySegment extends Segment {

  @NotNull private final String id;

  public ReplySegment(@NotNull String id) {
    super("reply");
    this.id = id;
  }

  @Override
  public Map<String, String> getData() {
    return Map.of("id", id);
  }
}
