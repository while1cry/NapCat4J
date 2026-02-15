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

    private static class VideoMessageData extends MessageData {
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

        private VideoMessageData(@NotNull String file,
                                  @Nullable String name,
                                  @Nullable String thumb,
                                  @Nullable String url,
                                  @Nullable String path,
                                  @Nullable String fileId,
                                  @Nullable String fileSize,
                                  @Nullable String fileUnique) {
            super("video");
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

    private static class RPSMessageData extends MessageData {
        @Nullable
        private final String result;

        private RPSMessageData(@Nullable String result) {
            super("rps");
            this.result = result;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("result", result));
        }
    }

    private static class DiceMessageData extends MessageData {
        @Nullable
        private final String result;

        private DiceMessageData(@Nullable String result) {
            super("dice");
            this.result = result;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("result", result));
        }
    }

    private static class ContactMessageData extends MessageData {
        @Nullable
        private final String id;

        private ContactMessageData(@Nullable String id) {
            super("qq");
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

    private static class ContactGroupMessageData extends MessageData {
        @Nullable
        private final String id;

        private ContactGroupMessageData(@Nullable String id) {
            super("group");
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

    private static class ReplyMessageData extends MessageData {
        @NotNull
        private final String id;

        private ReplyMessageData(@NotNull String id) {
            super("reply");
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

    private static class JsonMessageData extends MessageData {
        @NotNull
        private final String json;

        private JsonMessageData(@NotNull String json) {
            super("json");
            this.json = json;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .set("data", mapper.readTree(json)));
        }
    }

    private static class MFaceMessageData extends MessageData {
        @Nullable
        private final String emojiId;
        @Nullable
        private final String emojiPackageId;
        @Nullable
        private final String key;
        @Nullable
        private final String summary;

        private MFaceMessageData(@Nullable String emojiId,
                                 @Nullable String emojiPackageId,
                                 @Nullable String key,
                                 @Nullable String summary) {
            super("mface");
            this.emojiId = emojiId;
            this.emojiPackageId = emojiPackageId;
            this.key = key;
            this.summary = summary;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("emoji_id", emojiId)
                            .put("emoji_package_id", emojiPackageId)
                            .put("key", key)
                            .put("summary", summary));
        }
    }

    private static class FileMessageData extends MessageData {
        @Nullable
        private final String name;
        @NotNull
        private final String file;
        @Nullable
        private final String path;
        @Nullable
        private final String url;
        @Nullable
        private final String fileId;
        @Nullable
        private final String fileUnique;

        private FileMessageData(@Nullable String name,
                                @NotNull String file,
                                @Nullable String path,
                                @Nullable String url,
                                @Nullable String fileId,
                                @Nullable String fileUnique) {
            super("file");
            this.name = name;
            this.file = file;
            this.path = path;
            this.url = url;
            this.fileId = fileId;
            this.fileUnique = fileUnique;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.createObjectNode()
                            .put("name", name)
                            .put("file", file)
                            .put("path", path)
                            .put("url", url)
                            .put("file_id", fileId)
                            .put("file_unique", fileUnique));
        }
    }

    private static class UnknownMessageData extends MessageData {
        @Nullable
        private final String type;
        @Nullable
        private final String dataJson;

        private UnknownMessageData(@Nullable String type,
                                   @Nullable String dataJson) {
            super(type);
            this.type = type;
            this.dataJson = dataJson;
        }

        @Override
        protected JsonNode toJson() {
            return mapper.createObjectNode()
                    .put("type", type)
                    .set("data", mapper.readTree(dataJson));
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

        public MessageBuilder image(@Nullable String name,
                                    @Nullable String summary,
                                    @NotNull String file,
                                    @Nullable String subType,
                                    @Nullable String fileId,
                                    @Nullable String url,
                                    @Nullable String path,
                                    @Nullable String fileSize,
                                    @Nullable String fileUnique) {
            messages.add(new ImageMessageData(name, summary, file, subType, fileId, url, path, fileSize, fileUnique));
            return this;
        }

        public MessageBuilder record(@NotNull String file,
                                     @Nullable String name,
                                     @Nullable String thumb,
                                     @Nullable String url,
                                     @Nullable String path,
                                     @Nullable String fileId,
                                     @Nullable String fileSize,
                                     @Nullable String fileUnique) {
            messages.add(new RecordMessageData(file, name, thumb, url, path, fileId, fileSize, fileUnique));
            return this;
        }

        public MessageBuilder video(@NotNull String file,
                                    @Nullable String name,
                                    @Nullable String thumb,
                                    @Nullable String url,
                                    @Nullable String path,
                                    @Nullable String fileId,
                                    @Nullable String fileSize,
                                    @Nullable String fileUnique) {
            messages.add(new VideoMessageData(file, name, thumb, url, path, fileId, fileSize, fileUnique));
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

        public MessageBuilder rps() {
            messages.add(new RPSMessageData(null));
            return this;
        }

        public MessageBuilder dice() {
            messages.add(new DiceMessageData(null));
            return this;
        }
        public MessageBuilder contact(String qq) {
            messages.add(new ContactMessageData(qq));
            return this;
        }
        public MessageBuilder contactGroup(String group) {
            messages.add(new ContactGroupMessageData(group));
            return this;
        }
        public MessageBuilder reply(String id) {
            messages.add(new ReplyMessageData(id));
            return this;
        }

        public MessageBuilder json(String json) {
            messages.add(new JsonMessageData(json));
            return this;
        }

        public MessageBuilder mFace(@Nullable String emojiId,
                                    @Nullable String emojiPackageId,
                                    @Nullable String key,
                                    @Nullable String summary) {
            messages.add(new MFaceMessageData(emojiId, emojiPackageId, key, summary));
            return this;
        }

        public MessageBuilder file(@Nullable String name,
                                   @NotNull String file,
                                   @Nullable String path,
                                   @Nullable String url,
                                   @Nullable String fileId,
                                   @Nullable String fileUnique) {
            messages.add(new FileMessageData(name, file, path, url, fileId, fileUnique));
            return this;
        }

        public Message build() {
            return new Message(messages.toArray(new MessageData[0]));
        }
    }
}
