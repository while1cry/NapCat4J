package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@JsonSerialize(using = SegmentJsonSerializer.class)
@JsonDeserialize(using = SegmentJsonDeserializer.class)
public abstract sealed class Segment permits AtSegment, DiceSegment, FaceSegment, FileSegment, ImageSegment, JsonSegment, RPSSegment, RecordSegment, ReplySegment, TextSegment, UnknownSegment, VideoSegment {

    protected static final JsonMapper mapper = new JsonMapper();

    @Getter
    protected final String type;

    protected Segment(String type) {
        this.type = type;
    }

    public abstract Map<String, String> getData();
}
