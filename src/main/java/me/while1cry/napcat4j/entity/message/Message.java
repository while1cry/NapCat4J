package me.while1cry.napcat4j.entity.message;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Message {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    private final List<MessageData> dataList;

    private Message(List<MessageData> dataList) {
        this.dataList = dataList;
    }

    @SneakyThrows
    public String toJson() {
        ArrayNode arrayNode = mapper.createArrayNode();
        dataList.forEach(data -> arrayNode.add(data.toJson()));
        return mapper.writeValueAsString(arrayNode);
    }

    public String toCQ() {
        return dataList.stream()
                .map(MessageData::toCQ)
                .collect(Collectors.joining(""));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Message fromJson(String json) {
        try {
            MessageData[] dataArray = mapper.readValue(json, MessageData[].class);
            return new Message(Arrays.asList(dataArray));
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse message from JSON", e);
        }
    }

    public static Message fromJson(ArrayNode node) {
        try {
            MessageData[] dataArray = mapper.treeToValue(node, MessageData[].class);
            return new Message(Arrays.asList(dataArray));
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse message from JSON", e);
        }
    }

    public static Message fromCQ(String cqString) {
        List<String> cqCodes = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\[CQ:[^]]+]").matcher(cqString);

        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                cqCodes.add(cqString.substring(lastEnd, matcher.start()));
            }
            cqCodes.add(matcher.group());
            lastEnd = matcher.end();
        }
        if (lastEnd < cqString.length()) {
            cqCodes.add(cqString.substring(lastEnd));
        }
        Builder builder = builder();
        for (String part : cqCodes) {
            if (part.startsWith("[CQ:")) {
                String[] parts = part.substring(4, part.length() - 1).split(",", 2);
                String type = parts[0];

                Map<String, String> valueMap = Arrays.stream(
                                (parts.length > 1 ? parts[1] : "").split(","))
                        .map(s -> s.split("=", 2))
                        .collect(Collectors.toMap(
                                kv -> kv[0],
                                kv -> kv.length > 1 ? unescape(kv[1]) : ""
                        ));

                switch (type) {
                    case "face" -> {
                        if (!valueMap.containsKey("id")) {
                            throw new IllegalArgumentException("Face data requires at least one argument: id");
                        }
                        builder.face(valueMap.get("id"));
                    }
                    case "image" -> {
                        if (!valueMap.containsKey("file")) {
                            throw new IllegalArgumentException("Send side image data requires at least one argument: file");
                        }
                        builder.image(valueMap.get("file"), valueMap.get("summary"));
                    }
                    case "at" -> {
                        if (!valueMap.containsKey("qq")) {
                            throw new IllegalArgumentException("At data requires at least one argument: qq");
                        }
                        builder.at(valueMap.get("qq"));
                    }
                    case "reply" -> {
                        if (!valueMap.containsKey("id")) {
                            throw new IllegalArgumentException("Reply data requires at least one argument: id");
                        }
                        builder.reply(valueMap.get("id"));
                    }
                    case "json" -> {
                        if (!valueMap.containsKey("data")) {
                            throw new IllegalArgumentException("JSON data requires at least one argument: data");
                        }
                        builder.json(valueMap.get("data"));
                    }
                    default -> builder.dataList.add(new UnknownData(type, valueMap.toString()));
                }
            } else {
                builder.text(unescape(part));
            }
        }
        return builder.build();
    }

    public static class Builder {
        private final List<MessageData> dataList = new ArrayList<>();

        public Builder text(@NotNull String text) {
            dataList.add(new TextData(text));
            return this;
        }

        public Builder face(@NotNull String id) {
            dataList.add(new FaceData(id));
            return this;
        }

        public Builder image(
                @NotNull String file,
                @Nullable String summary) {
            dataList.add(new SendSideImageData(file, summary));
            return this;
        }

        public Builder at(@NotNull String qq) {
            dataList.add(new AtData(qq));
            return this;
        }

        public Builder atAll() {
            dataList.add(new AtData("all"));
            return this;
        }

        public Builder reply(@NotNull String messageId) {
            dataList.add(new ReplyData(messageId));
            return this;
        }

        public Builder json(@NotNull String json) {
            dataList.add(new JsonData(json));
            return this;
        }

        public Message build() {
            return new Message(dataList);
        }
    }

    @JsonDeserialize(using = MessageDataDeserializer.class)
    private static abstract class MessageData {

        @Getter
        @NotNull
        @JsonIgnore
        protected final String type;

        protected MessageData(@NotNull String type) {
            this.type = type;
        }

        protected ObjectNode toJson() {
            ObjectNode node = mapper.createObjectNode();
            node.put("type", type);
            node.set("data", mapper.valueToTree(this));
            return node;
        }

        protected abstract String toCQ();
    }

    @Getter
    public static final class TextData extends MessageData {

        @JsonProperty
        private final String text;

        private TextData(String text) {
            super("text");
            this.text = text;
        }

        @Override
        protected String toCQ() {
            return escape(text, false);
        }
    }

    @Getter
    public static final class FaceData extends MessageData {

        @JsonProperty
        private final String id;

        private FaceData(String id) {
            super("face");
            this.id = id;
        }

        @Override
        protected String toCQ() {
            return "[CQ:face,id=" + escape(id, true) + "]";
        }
    }

    @Getter
    private static final class SendSideImageData extends MessageData {

        @JsonProperty
        private final String file;

        @JsonProperty
        @Nullable
        private final String summary;

        private SendSideImageData(@NotNull String file, @Nullable String summary) {
            super("image");
            this.file = file;
            this.summary = summary;
        }

        @Override
        protected String toCQ() {
            StringBuilder sb = new StringBuilder("[CQ:image");
            sb.append(",file=").append(escape(file, true));
            if (summary != null)
                sb.append(",summary=").append(escape(summary, true));
            sb.append("]");
            return sb.toString();
        }
    }

    @Getter
    public static final class ImageData extends MessageData {

        @JsonProperty
        private final String file;

        @JsonProperty
        @Nullable
        private final String summary;

        @JsonProperty("sub_type")
        @Nullable
        private final String subType;

        @JsonProperty("file_id")
        private final String fileId;

        @JsonProperty
        private final String url;

        @JsonProperty
        private final String path;

        @JsonProperty("file_size")
        private final String fileSize;

        @JsonProperty("file_unique")
        private final String fileUnique;

        private ImageData(
                String file,
                @Nullable String summary,
                @Nullable String subType,
                String fileId,
                String url,
                String path,
                String fileSize,
                String fileUnique) {
            super("image");
            this.file = file;
            this.summary = summary;
            this.subType = subType;
            this.fileId = fileId;
            this.url = url;
            this.path = path;
            this.fileSize = fileSize;
            this.fileUnique = fileUnique;
        }

        @Override
        protected String toCQ() {
            StringBuilder sb = new StringBuilder("[CQ:image");
            sb.append(",file=").append(escape(file, true));
            if (summary != null)
                sb.append(",summary=").append(escape(summary, true));
            if (subType != null)
                sb.append(",sub_type=").append(escape(subType, true));
            sb.append(",file_id=").append(escape(fileId, true));
            sb.append(",url=").append(escape(url, true));
            sb.append(",path=").append(escape(path, true));
            sb.append(",file_size=").append(escape(fileSize, true));
            sb.append(",file_unique=").append(escape(fileUnique, true));
            sb.append("]");
            return sb.toString();
        }
    }

    @Getter
    public static final class AtData extends MessageData {

        @JsonProperty
        private final String qq;

        private AtData(String qq) {
            super("at");
            this.qq = qq;
        }

        @Override
        protected String toCQ() {
            return "[CQ:at,qq=" + escape(qq, true) + "]";
        }
    }

    @Getter
    public static final class ReplyData extends MessageData {

        @JsonProperty
        private final String id;

        private ReplyData(String id) {
            super("reply");
            this.id = id;
        }

        @Override
        protected String toCQ() {
            return "[CQ:reply,id=" + escape(id, true) + "]";
        }
    }

    @Getter
    public static final class JsonData extends MessageData {

        @JsonProperty
        private final String data;

        private JsonData(String json) {
            super("json");
            this.data = json;
        }

        @Override
        protected String toCQ() {
            return "[CQ:json,data=" + escape(data, true) + "]";
        }
    }

    @Getter
    @JsonSerialize(using = UnknownMessageDataSerializer.class)
    public static final class UnknownData extends MessageData {

        private final String data;

        private UnknownData(String type, String data) {
            super(type);
            this.data = data;
        }

        @Override
        protected String toCQ() {
            return "";
        }
    }

    private static final class MessageDataDeserializer extends JsonDeserializer<MessageData> {

        @Override
        public MessageData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectNode node = p.getCodec().readTree(p);
            String type = node.get("type").asText();
            return switch (type) {
                case "text" -> new TextData(node.get("data").get("text").asText());
                case "face" -> new FaceData(node.get("data").get("id").asText());
                default -> new UnknownData(type, node.get("data").toString());
            };
        }
    }

    private static final class UnknownMessageDataSerializer extends JsonSerializer<UnknownData> {

        @Override
        public void serialize(UnknownData value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();
            JsonNode data = mapper.readTree(value.data);
            node.put("type", value.type);
            node.set("data", data);
            gen.writeObject(node);
        }
    }

    private static String escape(String text,  boolean escapeComma) {
        String s = text
                .replace("&", "&amp;")
                .replace("[", "&#91;")
                .replace("]", "&#93;");
        if (escapeComma) {
            s = s.replace(",", "&#44;");
        }
        return s;
    }

    public static String unescape(String text) {
        return text
                .replace("&amp;", "&")
                .replace("&#91;", "[")
                .replace("&#93;", "]")
                .replace("&#44;", ",");
    }
}
