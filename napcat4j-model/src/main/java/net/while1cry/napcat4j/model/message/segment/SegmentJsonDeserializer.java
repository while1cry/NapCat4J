package net.while1cry.napcat4j.model.message.segment;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.json.JsonMapper;

public class SegmentJsonDeserializer extends ValueDeserializer<Segment> {

  private static final JsonMapper mapper = new JsonMapper();

  private static String getStringOrNull(JsonNode parent, String fieldName) {
    JsonNode field = parent.get(fieldName);
    return (field != null && !field.isNull() && !field.asString().isEmpty())
        ? field.asString()
        : null;
  }

  private static String getString(JsonNode parent, String fieldName) {
    JsonNode field = parent.get(fieldName);
    return field.asString();
  }

  @Override
  public Segment deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
    JsonNode node = mapper.readTree(p.getString());
    JsonNode data = node.get("data");
    String type = node.get("type").asString();

    return switch (type) {
      case "text" -> new TextSegment(getStringOrNull(data, "text"));
      case "face" -> new FaceSegment(getStringOrNull(data, "face"));
      case "image" ->
          new ImageSegment(
              getStringOrNull(data, "name"),
              getStringOrNull(data, "summary"),
              getStringOrNull(data, "sub_type"),
              getStringOrNull(data, "url"),
              getStringOrNull(data, "path"),
              getString(data, "file"),
              getStringOrNull(data, "fileId"),
              getStringOrNull(data, "fileSize"),
              getStringOrNull(data, "fileUnique"));
      case "record" ->
          new RecordSegment(
              getString(data, "file"),
              getStringOrNull(data, "name"),
              getStringOrNull(data, "url"),
              getStringOrNull(data, "path"),
              getStringOrNull(data, "file_id"),
              getStringOrNull(data, "file_size"),
              getStringOrNull(data, "file_unique"));
      case "video" ->
          new VideoSegment(
              getString(data, "file"),
              getStringOrNull(data, "name"),
              getStringOrNull(data, "thumb"),
              getStringOrNull(data, "url"),
              getStringOrNull(data, "path"),
              getStringOrNull(data, "file_id"),
              getStringOrNull(data, "file_size"),
              getStringOrNull(data, "file_unique"));
      case "at" -> new AtSegment(getString(data, "id"));
      case "rps" -> new RPSSegment(getStringOrNull(data, "result"));
      case "dice" -> new DiceSegment(getStringOrNull(data, "result"));
      case "reply" -> new ReplySegment(getString(data, "id"));
      case "json" -> new JsonSegment(getString(data, "data"));
      case "file" ->
          new FileSegment(
              getString(data, "file"),
              getStringOrNull(data, "name"),
              getStringOrNull(data, "url"),
              getStringOrNull(data, "path"),
              getStringOrNull(data, "file_id"),
              getStringOrNull(data, "file_size"),
              getStringOrNull(data, "file_unique"));
      case "poke" -> new PokeSegment(getString(data, "type"), getString(data, "id"));
      default -> new UnknownSegment(type, data);
    };
  }
}
