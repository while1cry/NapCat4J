package net.while1cry.napcat4j.model.message;

import java.util.Arrays;
import lombok.Getter;
import net.while1cry.napcat4j.model.message.segment.Segment;
import net.while1cry.napcat4j.model.message.segment.SegmentCQDeserializer;
import net.while1cry.napcat4j.model.message.segment.SegmentCQSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;

@Getter
public class Message {

  private static final JsonMapper mapper = new JsonMapper();
  private static final SegmentCQSerializer cqSerializer = new SegmentCQSerializer();
  private static final SegmentCQDeserializer cqDeserializer = new SegmentCQDeserializer();
  private final Segment[] segments;

  Message(Segment[] segments) {
    this.segments = segments;
  }

  public String toJson() {
    ArrayNode array = mapper.createArrayNode();
    Arrays.stream(segments).forEach(s -> array.add(mapper.valueToTree(s)));
    return array.toString();
  }

  public String toCQ() {
    StringBuilder sb = new StringBuilder();
    Arrays.stream(segments).forEach(s -> sb.append(cqSerializer.serialize(s)));
    return sb.toString();
  }

  public static Message fromJson(String json) {
    ArrayNode array = (ArrayNode) mapper.readTree(json);
    return new Message(
        array.values().stream()
            .map(e -> mapper.treeToValue(e, Segment.class))
            .toArray(Segment[]::new));
  }

  public static Message fromCQ(String cq) {
    String[] cqSegments = MessageUtil.splitToArray(cq);
    return new Message(
        Arrays.stream(cqSegments).map(cqDeserializer::deserialize).toArray(Segment[]::new));
  }

  public static MessageBuilder builder() {
    return new MessageBuilder();
  }
}
