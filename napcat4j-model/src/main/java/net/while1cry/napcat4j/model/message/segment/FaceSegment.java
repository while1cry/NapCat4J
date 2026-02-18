package net.while1cry.napcat4j.model.message.segment;

import java.util.Map;
import lombok.Getter;

@Getter
public final class FaceSegment extends Segment {

  private final String id;

  public FaceSegment(String id) {
    super("face");
    this.id = id;
  }

  @Override
  public Map<String, String> getData() {
    return Map.of("id", id);
  }
}
