package net.while1cry.napcat4j.model.message.segment;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

public class SegmentJsonSerializer extends ValueSerializer<Segment> {

    private static final JsonMapper mapper = new JsonMapper();

    @Override
    public void serialize(Segment value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        Map<String, String> map = new HashMap<>();
        value.getData().forEach((k, v) -> {
            if (v != null) map.put(k, v);
        });
        gen.writeTree(mapper.createObjectNode()
                .put("type", value.getType())
                .set("data", mapper.valueToTree(map)));
    }
}
