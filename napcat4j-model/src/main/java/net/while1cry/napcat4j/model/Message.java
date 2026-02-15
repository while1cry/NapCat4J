package net.while1cry.napcat4j.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private final static ObjectMapper mapper = new ObjectMapper();
    private final MessageData[] data;

    private Message(MessageData[] data) {
        this.data = data;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public String toJson() {
        ArrayNode root = mapper.createArrayNode();
        Arrays.stream(data).forEach(node -> root.add(node.toJson()));
        return root.toString();
    }

    public String toCQ() {
        return "";
    }

    public Message fromJson(String json) {
        return null;
    }

    public Message fromCQ(String cqCode) {
        return null;
    }

    static abstract class MessageData {
        protected final String type;

        protected MessageData(String type) {
            this.type = type;
        }

        protected abstract JsonNode toJson();
    }

    private static class TextMessageData extends MessageData {
        @NotNull
        private final String text;

        private TextMessageData(@NotNull String text) {
            super("text");
            this.text = text;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("text", text));
        }
    }

    private static class FaceMessageData extends MessageData {
        @NotNull
        private final String id;

        private FaceMessageData(@NotNull String id) {
            super("face");
            this.id = id;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("id", id));
        }
    }

    private static class ImageMessageData extends MessageData {
        @Nullable
        private final String name;
        @Nullable
        private final String summary;
        @NotNull
        private final String file;
        @Nullable
        private final String subType;
        @Nullable
        private final String fileId;
        @Nullable
        private final String url;
        @Nullable
        private final String path;
        @Nullable
        private final String fileSize;
        @Nullable
        private final String fileUnique;

        private ImageMessageData(@Nullable String name,
                                 @Nullable String summary,
                                 @NotNull String file,
                                 @Nullable String subType,
                                 @Nullable String fileId,
                                 @Nullable String url,
                                 @Nullable String path,
                                 @Nullable String fileSize,
                                 @Nullable String fileUnique) {
            super("image");
            this.name = name;
            this.summary = summary;
            this.file = file;
            this.subType = subType;
            this.fileId = fileId;
            this.url = url;
            this.path = path;
            this.fileSize = fileSize;
            this.fileUnique = fileUnique;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("name", name)
                            .put("summary", summary)
                            .put("file", file)
                            .put("sub_type", subType)
                            .put("file_id", fileId)
                            .put("url", url)
                            .put("path", path)
                            .put("file_size", fileSize)
                            .put("file_unique", fileUnique));
        }
    }

    private static class MarketFaceMessageData extends MessageData {
        @Nullable
        private final String name;
        @Nullable
        private final String summary;
        private final String file = "marketface";
        @Nullable
        private final String fileId;
        @Nullable
        private final String url;
        @Nullable
        private final String path;
        @Nullable
        private final String fileUnique;

        private MarketFaceMessageData(@Nullable String name,
                                 @Nullable String summary,
                                 @Nullable String fileId,
                                 @Nullable String url,
                                 @Nullable String path,
                                 @Nullable String fileUnique) {
            super("image");
            this.name = name;
            this.summary = summary;
            this.fileId = fileId;
            this.url = url;
            this.path = path;
            this.fileUnique = fileUnique;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("name", name)
                            .put("summary", summary)
                            .put("file", file)
                            .put("file_id", fileId)
                            .put("url", url)
                            .put("path", path)
                            .put("file_unique", fileUnique));
        }
    }

    private static class RecordMessageData extends MessageData {
        @NotNull
        private final String file;
        @Nullable
        private final String name;
        @Nullable
        private final String thumb;
        @Nullable
        private final String url;
        @Nullable
        private final String path;
        @Nullable
        private final String fileId;
        @Nullable
        private final String fileSize;
        @Nullable
        private final String fileUnique;

        private RecordMessageData(@NotNull String file,
                                  @Nullable String name,
                                  @Nullable String thumb,
                                  @Nullable String url,
                                  @Nullable String path,
                                  @Nullable String fileId,
                                  @Nullable String fileSize,
                                  @Nullable String fileUnique) {
            super("record");
            this.file = file;
            this.name = name;
            this.thumb = thumb;
            this.url = url;
            this.path = path;
            this.fileId = fileId;
            this.fileSize = fileSize;
            this.fileUnique = fileUnique;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("file", file)
                            .put("name", name)
                            .put("thumb", thumb)
                            .put("url", url)
                            .put("path", path)
                            .put("file_id", fileId)
                            .put("file_size", fileSize)
                            .put("file_unique", fileUnique));
        }
    }

    private static class AtMessageData extends MessageData {
        @NotNull
        private final String qq;

        private AtMessageData(@NotNull String qq) {
            super("at");
            this.qq = qq;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("qq", qq));
        }
    }

    public static class MessageBuilder {
        private List<MessageData> messages;

        private MessageBuilder() {
            messages = new ArrayList<>();
        }

        public MessageBuilder text(String text) {
            messages.add(new TextMessageData(text));
            return this;
        }

        public MessageBuilder nextLine() {
            messages.add(new TextMessageData("\n"));
            return this;
        }

        public MessageBuilder face(String id) {
            messages.add(new FaceMessageData(id));
            return this;
        }

        public MessageBuilder at(String qq) {
            messages.add(new AtMessageData(qq));
            return this;
        }

        public MessageBuilder atAll() {
            messages.add(new AtMessageData("all"));
            return this;
        }

        public Message build() {
            return new Message(messages.toArray(new MessageData[0]));
        }
    }
}
