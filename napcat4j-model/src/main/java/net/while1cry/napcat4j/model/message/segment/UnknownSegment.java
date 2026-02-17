package net.while1cry.napcat4j.model.message.segment;

import lombok.Getter;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

@Getter
public final class UnknownSegment extends Segment {

    private static final JsonMapper mapper = new JsonMapper();
    private final JsonNode data;

    public UnknownSegment(String type, String data) {
        super(type);
        this.data = mapper.readTree(data);
    }

    public UnknownSegment(String type, JsonNode data) {
        super(type);
        this.data = data;
    }

    @Override
    public Map<String, String> getData() {
        return mapper.convertValue(data, new TypeReference<>() {});
    }
}
