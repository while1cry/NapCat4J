package net.while1cry.napcat4j.model.message.segment;

import net.while1cry.napcat4j.model.message.MessageUtil;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

public class SegmentCQDeserializer {

    private static final JsonMapper mapper = new JsonMapper();

    public Segment deserialize(String cq) {
        if (cq.startsWith("[CQ:")) {
            String type = MessageUtil.getCQType(cq);
            Map<String, String> data = MessageUtil.parseToMap(cq);
            return switch (type) {
                case "face" -> new FaceSegment(MessageUtil.unescape(data.get("id"), true));
                case "image" -> new ImageSegment(
                        MessageUtil.unescape(data.get("name"), true),
                        MessageUtil.unescape(data.get("summary"), true),
                        MessageUtil.unescape(data.get("sub_type"), true),
                        MessageUtil.unescape(data.get("url"), true),
                        MessageUtil.unescape(data.get("path"), true),
                        MessageUtil.unescape(data.get("file"), true),
                        MessageUtil.unescape(data.get("fileId"), true),
                        MessageUtil.unescape(data.get("fileSize"), true),
                        MessageUtil.unescape(data.get("fileUnique"), true)
                );
                case "record" -> new RecordSegment(
                        MessageUtil.unescape(data.get("file"), true),
                        MessageUtil.unescape(data.get("name"), true),
                        MessageUtil.unescape(data.get("url"), true),
                        MessageUtil.unescape(data.get("path"), true),
                        MessageUtil.unescape(data.get("file_id"), true),
                        MessageUtil.unescape(data.get("file_size"), true),
                        MessageUtil.unescape(data.get("file_unique"), true)
                );
                case "video" -> new VideoSegment(
                        MessageUtil.unescape(data.get("file"), true),
                        MessageUtil.unescape(data.get("name"), true),
                        MessageUtil.unescape(data.get("thumb"), true),
                        MessageUtil.unescape(data.get("url"), true),
                        MessageUtil.unescape(data.get("path"), true),
                        MessageUtil.unescape(data.get("file_id"), true),
                        MessageUtil.unescape(data.get("file_size"), true),
                        MessageUtil.unescape(data.get("file_unique"), true)
                );
                case "at" -> new AtSegment(MessageUtil.unescape(data.get("id"), true));
                case "rps" -> new RPSSegment(MessageUtil.unescape(data.get("result"), true));
                case "dice" -> new DiceSegment(MessageUtil.unescape(data.get("result"), true));
                case "reply" -> new ReplySegment(MessageUtil.unescape(data.get("id"), true));
                case "json" -> new JsonSegment(MessageUtil.unescape(data.get("data"), true));
                case "file" -> new FileSegment(
                        MessageUtil.unescape(data.get("file"), true),
                        MessageUtil.unescape(data.get("name"), true),
                        MessageUtil.unescape(data.get("url"), true),
                        MessageUtil.unescape(data.get("path"), true),
                        MessageUtil.unescape(data.get("file_id"), true),
                        MessageUtil.unescape(data.get("file_size"), true),
                        MessageUtil.unescape(data.get("file_unique"), true)
                );
                default -> new UnknownSegment(type, mapper.valueToTree(data));
            };
        } else {
            return new TextSegment(MessageUtil.unescape(cq, false));
        }
    }
}
