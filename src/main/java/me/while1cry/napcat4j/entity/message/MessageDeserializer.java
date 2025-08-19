package me.while1cry.napcat4j.entity.message;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.while1cry.napcat4j.entity.message.data.*;

import java.io.IOException;

public class MessageDeserializer extends JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        ObjectNode node = mapper.readTree(p);

        String type = node.path("type").asText();
        JsonNode dataNode = node.path("data");

        MessageData data;

        switch (type) {
            case "text" -> data = new TextData(dataNode.path("text").asText());
            case "face" -> data = new FaceData(dataNode.path("id").asText());
            case "image" -> data = new ReceiveImageData(
                    dataNode.path("summary").asText(),
                    dataNode.path("file").asText(),
                    dataNode.path("sub_type").asText(),
                    dataNode.path("file_id").asText(),
                    dataNode.path("url").asText(),
                    dataNode.path("path").asText(),
                    dataNode.path("file_size").asText(),
                    dataNode.path("file_unique").asText()
            );
            case "at" -> data = new AtData(dataNode.path("qq").asText());
            case "reply" -> data = new ReplyData(dataNode.path("id").asText());
            case "json" -> data = new JsonData(dataNode.path("data").asText());
            default -> {
                data = new UnparseableData(dataNode);
            }
        }
        return new Message(type, data);
    }
}
