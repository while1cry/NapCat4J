package net.while1cry.napcat4j.model.message.segment;

import java.util.Map;
import lombok.Getter;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SegmentJsonSerializer.class)
@JsonDeserialize(using = SegmentJsonDeserializer.class)
public abstract sealed class Segment
    permits AtSegment,
        DiceSegment,
        FaceSegment,
        FileSegment,
        ImageSegment,
        JsonSegment,
        PokeSegment,
        RPSSegment,
        RecordSegment,
        ReplySegment,
        TextSegment,
        UnknownSegment,
        VideoSegment {

  @Getter protected final String type;

  protected Segment(String type) {
    this.type = type;
  }

  public abstract Map<String, String> getData();
}
